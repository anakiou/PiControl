package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.List;

import anakiou.com.picontrol.R;
import anakiou.com.picontrol.dao.OutputDAO;
import anakiou.com.picontrol.domain.Output;
import anakiou.com.picontrol.util.Constants;

public class OutputIntentService extends IntentService {

    private static final String TAG = "OutputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private OutputService outputService;

    private OutputDAO outputDAO;

    public static Intent newGetAllIntent(Context context) {
        Intent intent = new Intent(context, OutputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_OUTPUT_GET);

        return intent;
    }

    public static Intent newRefreshIntent(Context context) {
        Intent intent = new Intent(context, OutputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_OUTPUT_STATUS_ALL_GET);

        return intent;
    }

    public static Intent newNameSetIntent(Context context, int no) {
        Intent intent = new Intent(context, OutputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_OUTPUT_NAME_SET);
        intent.putExtra(Constants.EXTRA_NO, no);

        return intent;
    }

    public static Intent newOutputControlIntent(Context context, int no, boolean value) {
        Intent intent = new Intent(context, OutputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_OUTPUT_SINGLE_CONTROL);
        intent.putExtra(Constants.EXTRA_NO, no);
        intent.putExtra(Constants.EXTRA_CTRL_VALUE, value);

        return intent;
    }

    public static Intent newAllOutputsControlIntent(Context context, boolean[] values) {
        Intent intent = new Intent(context, OutputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_OUTPUT_CONTROL_ALL);
        intent.putExtra(Constants.EXTRA_CTRL_VALUE, values);

        return intent;
    }

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

            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.network_required_msg));

            return;
        }

        int operationType = intent.getIntExtra(Constants.EXTRA_OPERATION_TYPE, 0);

        switch (operationType) {
            case Constants.OP_OUTPUT_GET:
                handleGet();
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
            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_update_outputs));
            return;
        }

        for (Output out : outputDAO.findAll()) {
            outputDAO.delete(out);
        }

        for (Output out : outputsFromServer) {
            outputDAO.add(out);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.updated));
    }


    private void handleStatusAllGet() {

        List<Integer> statuses = outputService.getAllOutputsStatus();

        if (statuses.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_update_outputs));
            return;
        }

        List<Output> outputs = outputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Output out = outputs.get(i);
            out.setOutputStatus(statuses.get(i));
            outputDAO.update(out);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.updated));
    }

    private void handleNameSet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        for (Output in : outputDAO.findAll()) {
            if (in.getOutputNumber() == no) {
                String name = outputService.setOutputName(no, in.getName());

                if (name.equals(in.getName())) {
                    deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.saved));
                } else {
                    deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_set_name));
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
            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_send_control));
        }

        for (Output out : outputDAO.findAll()) {

            if (out.getOutputNumber() == no) {
                out.setOutputStatus(value);
                outputDAO.update(out);
                break;
            }
        }
        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.control_sent));
    }

    private void handleControlAll(Intent intent) {
        boolean[] values = intent.getBooleanArrayExtra(Constants.EXTRA_CTRL_VALUE);

        List<Integer> results = outputService.setAllControls(values);

        if (results.isEmpty()) {
            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_send_control));
        }

        List<Output> fromDb = outputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Output o = fromDb.get(i);
            o.setOutputStatus(results.get(i));
            outputDAO.update(o);
        }
        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.control_sent));
    }

    private void deliverResultToReceiver(int resultCode, String msg) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, msg);

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
