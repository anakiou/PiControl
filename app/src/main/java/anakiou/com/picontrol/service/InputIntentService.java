package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.List;

import anakiou.com.picontrol.dao.InputDAO;
import anakiou.com.picontrol.domain.Input;
import anakiou.com.picontrol.util.Constants;

public class InputIntentService extends IntentService {

    private static final String TAG = "InputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private InputService inputService;

    private InputDAO inputDAO;

    public InputIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        inputService = InputService.get();

        networkService = NetworkService.get(getApplicationContext());

        inputDAO = InputDAO.get(getApplicationContext());
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
                handleGet();
                break;
            case Constants.OP_INPUT_NAMES_GET:
                handleNamesGet();
                break;
            case Constants.OP_INPUT_SINGLE_STATUS_GET:
                handleSingleStatusGet(intent);
                break;
            case Constants.OP_INPUT_STATUS_ALL_GET:
                handleStatusAllGet();
                break;
            case Constants.OP_INPUT_NAME_SET:
                handleNameSet(intent);
                break;
        }
    }

    private void handleGet() {

        List<Input> inputsFromServer = inputService.getInputs();

        if (inputsFromServer.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        for (Input in : inputDAO.findAll()) {
            inputDAO.delete(in);
        }

        for (Input in : inputsFromServer) {
            inputDAO.add(in);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleNamesGet() {

        List<String> names = inputService.getInputNames();

        if (names.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        List<Input> inputs = inputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Input in = inputs.get(i);
            in.setName(names.get(i));
            inputDAO.update(in);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleSingleStatusGet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        int sts = inputService.getSingleInputStatus(no);

        if (sts == -1) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        for (Input in : inputDAO.findAll()) {
            if (in.getInputNumber() == no) {
                in.setInputStatus(sts);
                inputDAO.update(in);
                break;
            }
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleStatusAllGet() {
        List<Integer> statuses = inputService.getAllInputsStatus();

        if (statuses.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        List<Input> inputs = inputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Input in = inputs.get(i);
            in.setInputStatus(statuses.get(i));
            inputDAO.update(in);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleNameSet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        for (Input in : inputDAO.findAll()) {
            if (in.getInputNumber() == no) {
                String name = inputService.setInputName(no, in.getName());

                if (name.equals(in.getName())) {
                    deliverResultToReceiver(Constants.SUCCESS_RESULT);
                } else {
                    deliverResultToReceiver(Constants.FAILURE_RESULT);
                }
                break;
            }
        }
    }

    private void deliverResultToReceiver(int resultCode) {

        Bundle bundle = new Bundle();

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
