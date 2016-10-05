package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import anakiou.com.picontrol.util.Constants;

public class OutputIntentService extends IntentService {

    private static final String TAG = "OutputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private OutputService outputService;

    public OutputIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        outputService = OutputService.get();

        networkService = NetworkService.get(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        receiver = intent.getParcelableExtra(Constants.RECEIVER);

        if (!networkService.isNetworkAvailableAndConnected()) {

            deliverResultToReceiver(Constants.FAILURE_RESULT);

            return;
        }

        int operationType = intent.getIntExtra(Constants.EXTRA_OPERATION_TYPE, 0);

        switch (operationType) {
            case Constants.OP_OUTPUT_GET:
                handleGet(intent);
                break;
            case Constants.OP_OUTPUT_NAMES_GET:
                handleNamesGet(intent);
                break;
            case Constants.OP_OUTPUT_SINGLE_STATUS_GET:
                handleSingleStatusGet(intent);
                break;
            case Constants.OP_OUTPUT_STATUS_ALL_GET:
                handleStatusAllGet(intent);
                break;
            case Constants.OP_OUTPUT_NAME_SET:
                handleNameSet(intent);
                break;
            case Constants.OP_OUTPUT_SINGLE_CONTROL:
                handleSingleControl(intent);
                break;
            case Constants.OP_OUTPUT_CONTROL_ALL:
                handleControlAll(intent);
                break;
        }
    }

    private void handleGet(Intent intent) {

    }

    private void handleNamesGet(Intent intent) {

    }

    private void handleSingleStatusGet(Intent intent) {

    }

    private void handleStatusAllGet(Intent intent) {

    }

    private void handleNameSet(Intent intent) {

    }

    private void handleSingleControl(Intent intent) {

    }

    private void handleControlAll(Intent intent) {

    }

    private void deliverResultToReceiver(int resultCode) {

        Bundle bundle = new Bundle();

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
