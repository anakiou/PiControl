package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import java.util.List;

import anakiou.com.picontrol.domain.EventLog;
import anakiou.com.picontrol.util.Constants;

public class EventLogIntentService extends IntentService {

    private static final String TAG = "EventLogIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private EventLogService eventLogService;

    public static Intent newGetAllIntent(Context context) {

        Intent intent = new Intent(context, EventLogIntentService.class);

        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_EVENT_LOG_GET_ALL);

        return intent;
    }

    public static Intent newGetIntent(Context context, int no) {

        Intent intent = new Intent(context, EventLogIntentService.class);

        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_EVENT_LOG_GET);

        intent.putExtra(Constants.EXTRA_NO, no);

        return intent;
    }

    public static Intent newCountIntent(Context context) {

        Intent intent = new Intent(context, EventLogIntentService.class);

        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_EVENT_LOG_COUNT);

        return intent;
    }

    public EventLogIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        eventLogService = EventLogService.get();

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
            case Constants.OP_EVENT_LOG_GET_ALL:
                handleGetAll(intent);
                break;
            case Constants.OP_EVENT_LOG_GET:
                handleGet(intent);
                break;
            case Constants.OP_EVENT_LOG_COUNT:
                handleCount(intent);
            default:
        }
    }

    private void handleGetAll(Intent intent) {
        List<EventLog> eventLogList = eventLogService.getAllEvents();
    }

    private void handleGet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 10);

        List<EventLog> eventLogList = eventLogService.getLatestEvents(no);
    }

    private void handleCount(Intent intent) {
        int count = eventLogService.eventCount();
    }

    private void deliverResultToReceiver(int resultCode) {

        Bundle bundle = new Bundle();

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
