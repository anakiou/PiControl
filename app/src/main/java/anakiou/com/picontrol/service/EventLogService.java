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
