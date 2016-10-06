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

package anakiou.com.picontrol.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anakiou.com.picontrol.R;
import anakiou.com.picontrol.dao.InputDAO;
import anakiou.com.picontrol.domain.Input;
import anakiou.com.picontrol.service.InputIntentService;
import anakiou.com.picontrol.service.NetworkService;
import anakiou.com.picontrol.ui.activities.SettingsActivity;
import anakiou.com.picontrol.util.Constants;

public class InputsFragment extends Fragment {

    private InputsRefreshResultReceiver inputsRefreshResultReceiver;

    private SwipeRefreshLayout refreshLayout;

    private ImageView input0Image;
    private ImageView input1Image;
    private ImageView input2Image;
    private ImageView input3Image;
    private ImageView input4Image;
    private ImageView input5Image;
    private ImageView input6Image;
    private ImageView input7Image;

    private TextView input0StatusText;
    private TextView input1StatusText;
    private TextView input2StatusText;
    private TextView input3StatusText;
    private TextView input4StatusText;
    private TextView input5StatusText;
    private TextView input6StatusText;
    private TextView input7StatusText;

    private TextView input0NameText;
    private TextView input1NameText;
    private TextView input2NameText;
    private TextView input3NameText;
    private TextView input4NameText;
    private TextView input5NameText;
    private TextView input6NameText;
    private TextView input7NameText;

    private NetworkService networkService;

    private InputDAO inputDAO;

    private Handler refreshHandler;

    private InputsStatusRefreshRunnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        int refreshInterval = Integer.parseInt(sharedPref.getString(Constants.SET_REFRESH_INTERVAL, "5000"));

        networkService = NetworkService.get(getActivity().getApplicationContext());

        inputDAO = InputDAO.get(getActivity().getApplicationContext());

        inputsRefreshResultReceiver = new InputsRefreshResultReceiver(new Handler());

        refreshHandler = new Handler();

        runnable = new InputsStatusRefreshRunnable(refreshInterval);

        refreshHandler.postDelayed(runnable, 0000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_inputs, container, false);

        setupImageViews(view);
        setupStatusViews(view);
        setupNameViews(view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.inputs_swipe_layout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFromServer();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        refreshHandler.removeCallbacks(runnable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Intent i = new Intent(getContext(), SettingsActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    private void refreshFromServer() {
        if (networkService.isNetworkAvailableAndConnected()) {
            Intent intent = InputIntentService.newRefreshIntent(getContext());
            intent.putExtra(Constants.RECEIVER, inputsRefreshResultReceiver);
            getActivity().startService(intent);
        } else {
            refreshLayout.setRefreshing(false);

            if (getView() != null)
                Snackbar.make(getView(), getString(R.string.network_required_msg), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        List<Input> inputList = inputDAO.findAll();

        updatedImageViews(inputList);
        updatedStatusViews(inputList);
        updatedNameViews(inputList);
    }

    private void setupImageViews(View v) {
        input0Image = (ImageView) v.findViewById(R.id.input_0_image_view);
        input1Image = (ImageView) v.findViewById(R.id.input_1_image_view);
        input2Image = (ImageView) v.findViewById(R.id.input_2_image_view);
        input3Image = (ImageView) v.findViewById(R.id.input_3_image_view);
        input4Image = (ImageView) v.findViewById(R.id.input_4_image_view);
        input5Image = (ImageView) v.findViewById(R.id.input_5_image_view);
        input6Image = (ImageView) v.findViewById(R.id.input_6_image_view);
        input7Image = (ImageView) v.findViewById(R.id.input_7_image_view);
    }

    private void setupStatusViews(View v) {
        input0StatusText = (TextView) v.findViewById(R.id.input_0_status_view);
        input1StatusText = (TextView) v.findViewById(R.id.input_1_status_view);
        input2StatusText = (TextView) v.findViewById(R.id.input_2_status_view);
        input3StatusText = (TextView) v.findViewById(R.id.input_3_status_view);
        input4StatusText = (TextView) v.findViewById(R.id.input_4_status_view);
        input5StatusText = (TextView) v.findViewById(R.id.input_5_status_view);
        input6StatusText = (TextView) v.findViewById(R.id.input_6_status_view);
        input7StatusText = (TextView) v.findViewById(R.id.input_7_status_view);
    }

    private void setupNameViews(View v) {
        input0NameText = (TextView) v.findViewById(R.id.input_0_name_view);
        input1NameText = (TextView) v.findViewById(R.id.input_1_name_view);
        input2NameText = (TextView) v.findViewById(R.id.input_2_name_view);
        input3NameText = (TextView) v.findViewById(R.id.input_3_name_view);
        input4NameText = (TextView) v.findViewById(R.id.input_4_name_view);
        input5NameText = (TextView) v.findViewById(R.id.input_5_name_view);
        input6NameText = (TextView) v.findViewById(R.id.input_6_name_view);
        input7NameText = (TextView) v.findViewById(R.id.input_7_name_view);
    }

    private void updatedImageViews(List<Input> inputList) {
        input0Image.setImageDrawable(getDrawableForStatus(inputList.get(0).getInputStatus()));
        input1Image.setImageDrawable(getDrawableForStatus(inputList.get(1).getInputStatus()));
        input2Image.setImageDrawable(getDrawableForStatus(inputList.get(2).getInputStatus()));
        input3Image.setImageDrawable(getDrawableForStatus(inputList.get(3).getInputStatus()));
        input4Image.setImageDrawable(getDrawableForStatus(inputList.get(4).getInputStatus()));
        input5Image.setImageDrawable(getDrawableForStatus(inputList.get(5).getInputStatus()));
        input6Image.setImageDrawable(getDrawableForStatus(inputList.get(6).getInputStatus()));
        input7Image.setImageDrawable(getDrawableForStatus(inputList.get(7).getInputStatus()));
    }

    private void updatedStatusViews(List<Input> inputList) {
        input0StatusText.setText(getStringForStatus(inputList.get(0).getInputStatus()));
        input1StatusText.setText(getStringForStatus(inputList.get(1).getInputStatus()));
        input2StatusText.setText(getStringForStatus(inputList.get(2).getInputStatus()));
        input3StatusText.setText(getStringForStatus(inputList.get(3).getInputStatus()));
        input4StatusText.setText(getStringForStatus(inputList.get(4).getInputStatus()));
        input5StatusText.setText(getStringForStatus(inputList.get(5).getInputStatus()));
        input6StatusText.setText(getStringForStatus(inputList.get(6).getInputStatus()));
        input7StatusText.setText(getStringForStatus(inputList.get(7).getInputStatus()));
    }

    private void updatedNameViews(List<Input> inputList) {
        input0NameText.setText(inputList.get(0).getName());
        input1NameText.setText(inputList.get(1).getName());
        input2NameText.setText(inputList.get(2).getName());
        input3NameText.setText(inputList.get(3).getName());
        input4NameText.setText(inputList.get(4).getName());
        input5NameText.setText(inputList.get(5).getName());
        input6NameText.setText(inputList.get(6).getName());
        input7NameText.setText(inputList.get(7).getName());
    }

    private Drawable getDrawableForStatus(int status) {
        switch (status) {
            case 0:
                return getResources().getDrawable(R.drawable.ic_led_red);
            case 1:
                return getResources().getDrawable(R.drawable.ic_led_blue);
            default:
                return getResources().getDrawable(R.drawable.ic_led_grey);
        }
    }

    private String getStringForStatus(int status) {
        switch (status) {
            case 0:
                return getString(R.string.active);
            case 1:
                return getString(R.string.inactive);
            default:
                return getString(R.string.unknown);
        }
    }

    @SuppressLint("ParcelCreator")
    class InputsRefreshResultReceiver extends ResultReceiver {
        InputsRefreshResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            refreshLayout.setRefreshing(false);

            final String msg = resultData.getString(Constants.RESULT_DATA_KEY);

            boolean viewOk = getView() != null;

            if (viewOk) {
                updateUI();
            }

            if (viewOk && resultCode == Constants.FAILURE_RESULT) {
                Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    class InputsStatusRefreshRunnable implements Runnable {

        private int refreshInterval;

        InputsStatusRefreshRunnable(int refreshInterval) {
            this.refreshInterval = refreshInterval;
        }

        public void run() {
            refreshHandler.postDelayed(this, refreshInterval);
            refreshFromServer();
        }
    }
}
