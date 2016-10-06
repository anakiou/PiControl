package anakiou.com.picontrol.util;


public final class Constants {

    private static final String PACKAGE_NAME = "com.anakiou.picontrol";

    public static final String API_BASE_URL = "http://192.168.1.18:8080";

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
