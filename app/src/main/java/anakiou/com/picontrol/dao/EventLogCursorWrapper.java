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

import android.database.Cursor;
import android.database.CursorWrapper;

import anakiou.com.picontrol.domain.EventLog;

import static anakiou.com.picontrol.dao.DatabaseSchema.EventLogTable;

public class EventLogCursorWrapper extends CursorWrapper {

    public EventLogCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public EventLog get() {

        String msg = getString(getColumnIndex(EventLogTable.Cols.MSG));
        String ioName = getString(getColumnIndex(EventLogTable.Cols.IO_NAME));
        Integer ioValue = getInt(getColumnIndex(EventLogTable.Cols.IO_VALUE));
        Integer ioNumber = getInt(getColumnIndex(EventLogTable.Cols.IO_NUMBER));

        EventLog eventLog = new EventLog();
        eventLog.setMsg(msg);
        eventLog.setIoName(ioName);
        eventLog.setIoValue(ioValue);
        eventLog.setIoNumber(ioNumber);

        return eventLog;
    }
}
