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

import anakiou.com.picontrol.domain.Input;

import static anakiou.com.picontrol.dao.DatabaseSchema.InputTable;

public class InputCursorWrapper extends CursorWrapper {

    public InputCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Input get() {

        String name = getString(getColumnIndex(InputTable.Cols.NAME));
        Integer inputNumber = getInt(getColumnIndex(InputTable.Cols.INPUT_NUMBER));
        Integer inputStatus = getInt(getColumnIndex(InputTable.Cols.INPUT_STATUS));

        Input input = new Input();
        input.setName(name);
        input.setInputNumber(inputNumber);
        input.setInputStatus(inputStatus);

        return input;
    }
}
