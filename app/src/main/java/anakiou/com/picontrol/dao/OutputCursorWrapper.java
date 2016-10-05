/*
 * Copyright $todat.year . Anargyros Kiourkos.
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

import anakiou.com.picontrol.domain.Output;

import static anakiou.com.picontrol.dao.DatabaseSchema.OutputTable;

public class OutputCursorWrapper extends CursorWrapper {

    public OutputCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Output get() {

        String name = getString(getColumnIndex(OutputTable.Cols.NAME));
        Integer outputNumber = getInt(getColumnIndex(OutputTable.Cols.OUTPUT_NUMBER));
        Integer outputStatus = getInt(getColumnIndex(OutputTable.Cols.OUTPUT_STATUS));

        Output output = new Output();
        output.setName(name);
        output.setOutputNumber(outputNumber);
        output.setOutputStatus(outputStatus);

        return output;
    }
}
