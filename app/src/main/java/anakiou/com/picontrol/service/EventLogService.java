package anakiou.com.picontrol.service;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import anakiou.com.picontrol.domain.EventLog;
import retrofit2.Response;

public class EventLogService extends BaseService {

    private static final String TAG = "EventLogService";

    private static EventLogService eventLogService;

    public static EventLogService get() {
        if (eventLogService == null) {
            eventLogService = new EventLogService();
        }
        return eventLogService;
    }

    public Integer eventCount() {
        try {
            Response<Integer> response = prepareApi().eventCount().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting EVENT COUNT ", e);
        }

        return -1;
    }

    public List<EventLog> getAllEvents() {
        try {
            Response<List<EventLog>> response = prepareApi().getAllEvents().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting ALL EVENTS", e);
        }

        return Collections.emptyList();
    }

    public List<EventLog> getLatestEvents(int no) {
        try {
            Response<List<EventLog>> response = prepareApi().getLatestEvents(no).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting LATEST EVENTS", e);
        }

        return Collections.emptyList();
    }
}
