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

package anakiou.com.picontrol.util;


public final class Constants {

    //SETTINGS

    public static final String SET_REFRESH_INTERVAL = "refresh_interval";

    private static final String PACKAGE_NAME = "com.anakiou.picontrol";

    public static final String API_BASE_URL = "http://192.168.1.2:8085";

    public static final int OK_200 = 200;

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    //EXTRAS

    public static final String EXTRA_OPERATION_TYPE = PACKAGE_NAME + ".OPERATION_TYPE";

    public static final String EXTRA_NO = PACKAGE_NAME + ".NO";

    public static final String EXTRA_CTRL_VALUE = PACKAGE_NAME + ".CTRL_VALUE";

    //RESULT CODES

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    //INPUTS OPERATION TYPES

    public static final int OP_INPUT_GET = 0;

    public static final int OP_INPUT_STATUS_ALL_GET = 1;

    public static final int OP_INPUT_NAME_SET = 2;

    // OUTPUTS OPERATION TYPES

    public static final int OP_OUTPUT_GET = 0;

    public static final int OP_OUTPUT_STATUS_ALL_GET = 1;

    public static final int OP_OUTPUT_NAME_SET = 2;

    public static final int OP_OUTPUT_SINGLE_CONTROL = 3;

    public static final int OP_OUTPUT_CONTROL_ALL = 4;

    //EVENTS OPERATION TYPES

    public static final int OP_EVENT_LOG_COUNT = 0;

    public static final int OP_EVENT_LOG_GET_ALL = 1;

    public static final int OP_EVENT_LOG_GET = 2;

}
