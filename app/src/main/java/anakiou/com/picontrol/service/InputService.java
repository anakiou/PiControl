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

    public List<String> getInputNames() {
        try {
            Response<List<String>> response = prepareApi().getInputNames().execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting INPUT NAMES", e);
        }

        return Collections.emptyList();
    }

    public Integer getSingleInputStatus(int inputNo) {
        try {
            Response<Integer> response = prepareApi().getSingleInputStatus(inputNo).execute();

            if (isResponseOk(response)) return response.body();

        } catch (IOException e) {
            Log.e(TAG, "Error getting SINGLE INPUT " + inputNo + " STATUS ", e);
        }

        return -1;
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
