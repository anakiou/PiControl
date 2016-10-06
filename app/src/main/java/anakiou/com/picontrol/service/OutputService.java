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

import anakiou.com.picontrol.domain.Output;
import retrofit2.Response;

public class OutputService extends BaseService {

    private static final String TAG = "OutputService";

    private static OutputService outputService;

    public static OutputService get() {
        if (outputService == null) {
            outputService = new OutputService();
        }
        return outputService;
    }

    private OutputService() {
    }

    public List<Output> getOutputs() {
        try {
            Response<List<Output>> response = prepareApi().getOutputs().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting INPUTS", e);
        }

        return Collections.emptyList();
    }

    public List<Integer> getAllOutputsStatus() {
        try {
            Response<List<Integer>> response = prepareApi().getAllOutputsStatus().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e ) {
            Log.e(TAG, "Error getting ALL OUTPUTS STATUS ", e);
        }

        return Collections.emptyList();
    }

    public String setOutputName(int outputNo, String name) {
        try {
            Response<String> response = prepareApi().setOutputName(outputNo, name).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error setting SINGLE OUTPUT " + outputNo + " NAME to " + name, e);
        }

        return "";
    }

    public Integer setControl(int outputNo, boolean value) {
        try {
            Response<Integer> response = prepareApi().setControl(outputNo, value).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error setting SINGLE OUTPUT " + outputNo + " to " + value, e);
        }

        return -1;
    }

    public List<Integer> setAllControls(boolean[] values) {
        try {
            Response<List<Integer>> response = prepareApi().setAllControls(values).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error setting ALL CONTROLS", e);
        }

        return Collections.emptyList();
    }
}
