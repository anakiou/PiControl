package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import anakiou.com.picontrol.util.Constants;

public class InOutIntentService extends IntentService {

    private static final String TAG = "InOutIntentService";
    private static final String EXTRA_OPERATION_TYPE = "operationType";
    private static final String EXTRA_IO_NO = "ioNo";
    private static final String EXTRA_CTRL_VALUE = "ctrlValue";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private InOutService inOutService;


    public static Intent newIntent(Context context, int operationType, int ioNo, boolean ctrlValue) {

        Intent intent = new Intent(context, InOutIntentService.class);

        intent.putExtra(EXTRA_OPERATION_TYPE, operationType);

        intent.putExtra(EXTRA_IO_NO, ioNo);

        intent.putExtra(EXTRA_CTRL_VALUE, ctrlValue);

        return intent;
    }

    public InOutIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        inOutService = InOutService.get(getApplicationContext());
        networkService = NetworkService.get(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        receiver = intent.getParcelableExtra(Constants.RECEIVER);

        int operationType = intent.getIntExtra(EXTRA_OPERATION_TYPE, 0);

        int ioNo = intent.getIntExtra(EXTRA_OPERATION_TYPE, 0);

        boolean ctrlValue = intent.getBooleanExtra(EXTRA_CTRL_VALUE, false);

        String msg = "";

        if (!networkService.isNetworkAvailableAndConnected()) {

            deliverResultToReceiver(Constants.FAILURE_RESULT, "", -1);

            return;
        }

        int value = -1;
        String key = "";

        switch (operationType) {

            case Constants.OUTPUT_SET:

                value = inOutService.controlOutput(ioNo, ctrlValue);

                key = "OUTPUT_" + ioNo;

                break;
            case Constants.OUTPUT_STATUS_REFRESH:

                value = inOutService.getOutputStatus(ioNo);

                key = "OUTPUT_" + ioNo;

                break;
            case Constants.INPUT_STATUS_REFRESH:

                value = inOutService.getInputStatus(ioNo);

                key = "INPUT_" + ioNo;

            default:
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT, key, value);
    }

    private void deliverResultToReceiver(int resultCode, String key, int value) {

        Bundle bundle = new Bundle();

        bundle.putInt(key, value);

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
