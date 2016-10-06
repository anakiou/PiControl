package anakiou.com.picontrol.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.List;

import anakiou.com.picontrol.R;
import anakiou.com.picontrol.dao.InputDAO;
import anakiou.com.picontrol.domain.Input;
import anakiou.com.picontrol.util.Constants;

public class InputIntentService extends IntentService {

    private static final String TAG = "InputIntentService";

    private ResultReceiver receiver;

    private NetworkService networkService;

    private InputService inputService;

    private InputDAO inputDAO;

    public static Intent newGetAllIntent(Context context) {

        Intent intent = new Intent(context, InputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_INPUT_GET);

        return intent;
    }

    public static Intent newRefreshIntent(Context context) {

        Intent intent = new Intent(context, InputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_INPUT_STATUS_ALL_GET);

        return intent;
    }

    public static Intent newNameSetIntent(Context context, int no) {

        Intent intent = new Intent(context, InputIntentService.class);
        intent.putExtra(Constants.EXTRA_OPERATION_TYPE, Constants.OP_INPUT_NAME_SET);
        intent.putExtra(Constants.EXTRA_NO, no);

        return intent;
    }

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

            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.network_required_msg));

            return;
        }

        int operationType = intent.getIntExtra(Constants.EXTRA_OPERATION_TYPE, 0);

        switch (operationType) {
            case Constants.OP_INPUT_GET:
                handleGet();
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
            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_update_inputs));
            return;
        }

        for (Input in : inputDAO.findAll()) {
            inputDAO.delete(in);
        }

        for (Input in : inputsFromServer) {
            inputDAO.add(in);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.updated));
    }

    private void handleStatusAllGet() {
        List<Integer> statuses = inputService.getAllInputsStatus();

        if (statuses.isEmpty()) {

            for (Input in : inputDAO.findAll()) {
                in.setInputStatus(-1);
                inputDAO.update(in);
            }

            deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_update_inputs));
            return;
        }

        List<Input> inputs = inputDAO.findAll();

        for (int i = 0; i < 8; i++) {
            Input in = inputs.get(i);
            in.setInputStatus(statuses.get(i));
            inputDAO.update(in);
        }

        deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.updated));
    }

    private void handleNameSet(Intent intent) {

        int no = intent.getIntExtra(Constants.EXTRA_NO, 0);

        for (Input in : inputDAO.findAll()) {
            if (in.getInputNumber() == no) {
                String name = inputService.setInputName(no, in.getName());

                if (name.equals(in.getName())) {
                    deliverResultToReceiver(Constants.SUCCESS_RESULT, getString(R.string.saved));
                } else {
                    deliverResultToReceiver(Constants.FAILURE_RESULT, getString(R.string.failed_to_set_name));
                }
                break;
            }
        }
    }

    private void deliverResultToReceiver(int resultCode, String msg) {

        Bundle bundle = new Bundle();

        bundle.putString(Constants.RESULT_DATA_KEY, msg);

        if (receiver != null) {
            receiver.send(resultCode, bundle);
        }
    }
}
