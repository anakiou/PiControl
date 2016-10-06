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


public class DatabaseSchema {


    public static final class InputTable {

        public static String NAME = "inputs";

        public static final class Cols {

            public static final String NAME = "name";

            public static final String INPUT_NUMBER = "input_number";

            public static final String INPUT_STATUS = "input_status";
        }

        public static final String CREATE_SQL() {
            return "create table "
                    + NAME
                    + "("
                    + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Cols.NAME + " TEXT NOT NULL, "
                    + Cols.INPUT_NUMBER + " INTEGER NOT NULL UNIQUE, "
                    + Cols.INPUT_STATUS + " INTEGER)";
        }

        public static final String COUNT_SQL() {
            return "SELECT COUNT(*) FROM " + NAME;
        }
    }

    public static final class OutputTable {

        public static String NAME = "outputs";

        public static final class Cols {

            public static final String NAME = "name";

            public static final String OUTPUT_NUMBER = "output_number";

            public static final String OUTPUT_STATUS = "output_status";
        }

        public static final String CREATE_SQL() {
            return "create table "
                    + NAME
                    + "("
                    + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Cols.NAME + " TEXT NOT NULL, "
                    + Cols.OUTPUT_NUMBER + " INTEGER NOT NULL UNIQUE, "
                    + Cols.OUTPUT_STATUS + " INTEGER)";
        }

        public static final String COUNT_SQL() {
            return "SELECT COUNT(*) FROM " + NAME;
        }
    }

    public static final class EventLogTable {

        public static final String NAME = "event_logs";

        public static final class Cols {

            public static final String MSG = "msg";

            public static final String IO_NAME = "io_name";

            public static final String IO_VALUE = "io_value";

            public static final String IO_NUMBER = "io_number";

            public static final String DATE_CREATED = "date_created";
        }

        public static final String CREATE_SQL() {
            return "create table "
                    + NAME
                    + "("
                    + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Cols.MSG + " TEXT NOT NULL, "
                    + Cols.IO_NAME + " TEXT, "
                    + Cols.IO_VALUE + " INTEGER, "
                    + Cols.IO_NUMBER + " INTEGER, "
                    + Cols.DATE_CREATED + " INTEGER NOT NULL)";
        }

        public static final String COUNT_SQL() {
            return "SELECT COUNT(*) FROM " + NAME;
        }
    }
}

