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

import anakiou.com.picontrol.domain.Output;

import static anakiou.com.picontrol.dao.DatabaseSchema.OutputTable;

public class OutputDAO {

    private static final String TAG = "OutputDAO";

    private static OutputDAO outputDAO;

    private SQLiteDatabase database;

    private OutputDAO(Context context) {
        database = DatabaseHelper.getInstance(context).getWritableDatabase();
    }

    public static OutputDAO get(Context context) {

        if (outputDAO == null) {
            outputDAO = new OutputDAO(context);
        }
        return outputDAO;
    }

    public int count() {
        Cursor cursor = database.rawQuery(OutputTable.COUNT_SQL(), null);

        cursor.moveToFirst();
        int total = cursor.getInt(0);
        cursor.close();

        return total;
    }

    public List<Output> findAll() {

        List<Output> outputs = new ArrayList<>();

        OutputCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                outputs.add(cursor.get());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return outputs;
    }

    public long add(Output output) {
        ContentValues values = getContentValues(output);

        long id = -1;

        database.beginTransaction();

        try {

            id = database.insert(OutputTable.NAME, null, values);
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
            return id;
        }
    }

    public void update(Output output) {
        final ContentValues values = getContentValues(output);

        final String where = OutputTable.Cols.OUTPUT_NUMBER + " = ?";
        final String[] args = new String[]{output.getOutputNumber() + ""};

        database.beginTransaction();

        try {
            database.update(OutputTable.NAME, values, where, args);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public int delete(Output output) {

        final String where = OutputTable.Cols.OUTPUT_NUMBER + " = ?";
        final String[] args = new String[]{output.getOutputNumber() + ""};

        int rowsAffected = 0;

        database.beginTransaction();

        try {
            rowsAffected = database.delete(OutputTable.NAME, where, args);

            database.setTransactionSuccessful();

        } catch (Exception ex) {

            Log.e(TAG, "Could not delete Output", ex);

        } finally {

            database.endTransaction();
            return rowsAffected;
        }
    }

    private ContentValues getContentValues(Output output) {
        ContentValues values = new ContentValues();

        values.put(OutputTable.Cols.NAME, output.getName());
        values.put(OutputTable.Cols.OUTPUT_NUMBER, output.getOutputNumber());
        values.put(OutputTable.Cols.OUTPUT_STATUS, output.getOutputStatus());

        return values;
    }

    private OutputCursorWrapper query(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(
                OutputTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                OutputTable.Cols.OUTPUT_NUMBER + " ASC" // orderBy
        );
        return new OutputCursorWrapper(cursor);
    }
}
