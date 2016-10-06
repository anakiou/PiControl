/*
 * Copyright 2016 . Anargyros Kiourkos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package anakiou.com.picontrol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import anakiou.com.picontrol.domain.Input;

import static anakiou.com.picontrol.dao.DatabaseSchema.InputTable;

public class InputDAO {

    private static final String TAG = "InputDAO";

    private static InputDAO inputDAO;

    private SQLiteDatabase database;

    private InputDAO(Context context) {
        database = DatabaseHelper.getInstance(context).getWritableDatabase();
    }

    public static InputDAO get(Context context) {

        if (inputDAO == null) {
            inputDAO = new InputDAO(context);
        }
        return inputDAO;
    }

    public int count() {
        Cursor cursor = database.rawQuery(InputTable.COUNT_SQL(), null);

        cursor.moveToFirst();
        int total = cursor.getInt(0);
        cursor.close();

        return total;
    }

    public List<Input> findAll() {

        List<Input> inputs = new ArrayList<>();

        InputCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                inputs.add(cursor.get());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return inputs;
    }

    public long add(Input input) {
        ContentValues values = getContentValues(input);

        long id = -1;

        database.beginTransaction();

        try {

            id = database.insert(InputTable.NAME, null, values);
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
            return id;
        }
    }

    public void update(Input input) {
        final ContentValues values = getContentValues(input);

        final String where = InputTable.Cols.INPUT_NUMBER + " = ?";
        final String[] args = new String[]{input.getInputNumber() + ""};

        database.beginTransaction();

        try {
            database.update(InputTable.NAME, values, where, args);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public int delete(Input input) {

        final String where = InputTable.Cols.INPUT_NUMBER + " = ?";
        final String[] args = new String[]{input.getInputNumber() + ""};

        int rowsAffected = 0;

        database.beginTransaction();

        try {
            rowsAffected = database.delete(InputTable.NAME, where, args);

            database.setTransactionSuccessful();

        } catch (Exception ex) {

            Log.e(TAG, "Could not delete Input", ex);

        } finally {

            database.endTransaction();
            return rowsAffected;
        }
    }

    private ContentValues getContentValues(Input input) {
        ContentValues values = new ContentValues();

        values.put(InputTable.Cols.NAME, input.getName());
        values.put(InputTable.Cols.INPUT_NUMBER, input.getInputNumber());
        values.put(InputTable.Cols.INPUT_STATUS, input.getInputStatus());

        return values;
    }

    private InputCursorWrapper query(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(
                InputTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                InputTable.Cols.INPUT_NUMBER + " ASC" // orderBy
        );
        return new InputCursorWrapper(cursor);
    }
}
