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
    private static final String EXTRA_REPORT_ID = "reportID";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private InOutService inOutService;


    public static Intent newIntent(Context context, int operationType, String reportID) {
        Intent intent = new Intent(context, InOutIntentService.class);

        intent.putExtra(EXTRA_OPERATION_TYPE, operationType);

        intent.putExtra(EXTRA_REPORT_ID, reportID);

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

        String reportID = intent.getStringExtra(EXTRA_REPORT_ID);

        String msg = "";

        if (!networkService.isNetworkAvailableAndConnected()) {

            deliverResultToReceiver(Constants.FAILURE_RESULT, "", -1);

            return;
        }

        int value = -1;
        String key = "";

        switch (operationType) {
            case Constants.OUTPUT_SET:

                break;
            case Constants.OUTPUT_STATUS_REFRESH:

                break;
            case Constants.INPUT_STATUS_REFRESH:
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
