package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import anakiou.com.picontrol.util.Constants;

public class InputIntentService extends IntentService {

    private static final String TAG = "InputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private InputService inputService;

    public InputIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        inputService = InputService.get();

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
            case Constants.OP_INPUT_GET:
                handleGet(intent);
                break;
            case Constants.OP_INPUT_NAMES_GET:
                handleNamesGet(intent);
                break;
            case Constants.OP_INPUT_SINGLE_STATUS_GET:
                handleSingleStatusGet(intent);
                break;
            case Constants.OP_INPUT_STATUS_ALL_GET:
                handleStatusAllGet(intent);
                break;
            case Constants.OP_INPUT_NAME_SET:
                handleNameSet(intent);
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

    private void deliverResultToReceiver(int resultCode) {

        Bundle bundle = new Bundle();

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
