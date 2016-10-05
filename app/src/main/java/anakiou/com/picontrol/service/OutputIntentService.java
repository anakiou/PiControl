package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.List;

import anakiou.com.picontrol.dao.OutputDAO;
import anakiou.com.picontrol.domain.Output;
import anakiou.com.picontrol.util.Constants;

public class OutputIntentService extends IntentService {

    private static final String TAG = "OutputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private OutputService outputService;

    private OutputDAO outputDAO;

    public OutputIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        outputService = OutputService.get();

        networkService = NetworkService.get(getApplicationContext());

        outputDAO = OutputDAO.get(getApplicationContext());
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
                handleGet();
                break;
            case Constants.OP_OUTPUT_NAMES_GET:
                handleNamesGet();
                break;
            case Constants.OP_OUTPUT_SINGLE_STATUS_GET:
                handleSingleStatusGet(intent);
                break;
            case Constants.OP_OUTPUT_STATUS_ALL_GET:
                handleStatusAllGet();
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

    private void handleGet() {

        List<Output> outputsFromServer = outputService.getOutputs();

        if (outputsFromServer.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        for (Output out : outputDAO.findAll()) {
            outputDAO.delete(out);
        }

        for (Output out : outputsFromServer) {
            outputDAO.add(out);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleNamesGet() {

        List<String> names = outputService.getOutputNames();

        if (names.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        List<Output> outputs = outputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Output out = outputs.get(i);
            out.setName(names.get(i));
            outputDAO.update(out);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleSingleStatusGet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        int sts = outputService.getSingleOutputStatus(no);

        if (sts == -1) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        for (Output out : outputDAO.findAll()) {
            if (out.getOutputNumber() == no) {
                out.setOutputStatus(sts);
                outputDAO.update(out);
                break;
            }
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleStatusAllGet() {

        List<Integer> statuses = outputService.getAllOutputsStatus();

        if (statuses.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
            return;
        }

        List<Output> outputs = outputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Output out = outputs.get(i);
            out.setOutputStatus(statuses.get(i));
            outputDAO.update(out);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleNameSet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        for (Output in : outputDAO.findAll()) {
            if (in.getOutputNumber() == no) {
                String name = outputService.setOutputName(no, in.getName());

                if (name.equals(in.getName())) {
                    deliverResultToReceiver(Constants.SUCCESS_RESULT);
                } else {
                    deliverResultToReceiver(Constants.FAILURE_RESULT);
                }
                break;
            }
        }
    }

    private void handleSingleControl(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        boolean ctrlValue = intent.getBooleanExtra(Constants.EXTRA_CTRL_VALUE, false);

        int value = outputService.setControl(no, ctrlValue);

        if (value == -1) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
        }

        for (Output out : outputDAO.findAll()) {

            if (out.getOutputNumber() == no) {
                out.setOutputStatus(value);
                outputDAO.update(out);
                break;
            }
        }
        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void handleControlAll(Intent intent) {
        boolean[] values = intent.getBooleanArrayExtra(Constants.EXTRA_CTRL_VALUE);

        List<Integer> results = outputService.setAllControls(values);

        if (results.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT);
        }

        List<Output> fromDb = outputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Output o = fromDb.get(i);
            o.setOutputStatus(results.get(i));
            outputDAO.update(o);
        }
        deliverResultToReceiver(Constants.SUCCESS_RESULT);
    }

    private void deliverResultToReceiver(int resultCode) {

        Bundle bundle = new Bundle();

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
