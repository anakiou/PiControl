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

import anakiou.com.picontrol.domain.EventLog;

import static anakiou.com.picontrol.dao.DatabaseSchema.EventLogTable;

public class EventLogDAO {

    private static final String TAG = "EventLogDAO";

    private static EventLogDAO eventLogDAO;

    private SQLiteDatabase database;

    private EventLogDAO(Context context) {
        database = DatabaseHelper.getInstance(context).getWritableDatabase();
    }

    public static EventLogDAO get(Context context) {

        if (eventLogDAO == null) {
            eventLogDAO = new EventLogDAO(context);
        }
        return eventLogDAO;
    }

    public int count() {
        Cursor cursor = database.rawQuery(EventLogTable.COUNT_SQL(), null);

        cursor.moveToFirst();
        int total = cursor.getInt(0);
        cursor.close();

        return total;
    }

    public List<EventLog> findAll() {

        List<EventLog> eventLogs = new ArrayList<>();

        EventLogCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                eventLogs.add(cursor.get());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return eventLogs;
    }

    public long add(EventLog eventLog) {
        ContentValues values = getContentValues(eventLog);

        long id = -1;

        database.beginTransaction();

        try {

            id = database.insert(EventLogTable.NAME, null, values);
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
            return id;
        }
    }

    public int deleteAll() {

        final String where = EventLogTable.Cols.MSG + " IS NOT  NULL";

        int rowsAffected = 0;

        database.beginTransaction();

        try {
            rowsAffected = database.delete(EventLogTable.NAME, where, null);

            database.setTransactionSuccessful();

        } catch (Exception ex) {

            Log.e(TAG, "Could not delete EventLog", ex);

        } finally {

            database.endTransaction();
            return rowsAffected;
        }
    }

    private ContentValues getContentValues(EventLog eventLog) {
        ContentValues values = new ContentValues();

        values.put(EventLogTable.Cols.MSG, eventLog.getMsg());
        values.put(EventLogTable.Cols.IO_NAME, eventLog.getIoName());
        values.put(EventLogTable.Cols.IO_VALUE, eventLog.getIoValue());
        values.put(EventLogTable.Cols.IO_NUMBER, eventLog.getIoNumber());
        values.put(EventLogTable.Cols.DATE_CREATED, eventLog.getDateCreated());


        return values;
    }

    private EventLogCursorWrapper query(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(
                EventLogTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                EventLogTable.Cols.DATE_CREATED + " DESC" // orderBy
        );
        return new EventLogCursorWrapper(cursor);
    }
}
