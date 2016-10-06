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

import anakiou.com.picontrol.domain.Input;
import retrofit2.Response;

public class InputService extends BaseService {

    private static final String TAG = "InputService";

    private static InputService inputService;

    public static InputService get() {
        if (inputService == null) {
            inputService = new InputService();
        }
        return inputService;
    }

    private InputService() {
    }

    public List<Input> getInputs() {
        try {
            Response<List<Input>> response = prepareApi().getInputs().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting INPUTS", e);
        }

        return Collections.emptyList();
    }

    public List<Integer> getAllInputsStatus() {
        try {
            Response<List<Integer>> response = prepareApi().getAllInputsStatus().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting ALL INPUTS STATUS ", e);
        }

        return Collections.emptyList();
    }

    public String setInputName(int inputNo, String name) {
        try {
            Response<String> response = prepareApi().setInputName(inputNo, name).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error setting SINGLE INPUT " + inputNo + " NAME to " + name, e);
        }

        return "";
    }
}
