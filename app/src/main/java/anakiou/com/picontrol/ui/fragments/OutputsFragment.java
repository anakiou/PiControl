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
import android.widget.ToggleButton;

import java.util.List;

import anakiou.com.picontrol.R;
import anakiou.com.picontrol.dao.OutputDAO;
import anakiou.com.picontrol.domain.Output;
import anakiou.com.picontrol.service.NetworkService;
import anakiou.com.picontrol.service.OutputIntentService;
import anakiou.com.picontrol.ui.activities.SettingsActivity;
import anakiou.com.picontrol.util.Constants;

public class OutputsFragment extends Fragment {

    private OutputsResultReceiver outputsResultReceiver;

    private SwipeRefreshLayout refreshLayout;

    private ImageView out0Image;
    private ImageView out1Image;
    private ImageView out2Image;
    private ImageView out3Image;
    private ImageView out4Image;
    private ImageView out5Image;
    private ImageView out6Image;
    private ImageView out7Image;

    private ToggleButton out0Btn;
    private ToggleButton out1Btn;
    private ToggleButton out2Btn;
    private ToggleButton out3Btn;
    private ToggleButton out4Btn;
    private ToggleButton out5Btn;
    private ToggleButton out6Btn;
    private ToggleButton out7Btn;

    private TextView out0NameText;
    private TextView out1NameText;
    private TextView out2NameText;
    private TextView out3NameText;
    private TextView out4NameText;
    private TextView out5NameText;
    private TextView out6NameText;
    private TextView out7NameText;

    private NetworkService networkService;

    private OutputDAO outputDAO;

    private Handler refreshHandler;

    private OutputsStatusRefreshRunnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        int refreshInterval = Integer.parseInt(sharedPref.getString(Constants.SET_REFRESH_INTERVAL, "5000"));

        networkService = NetworkService.get(getActivity().getApplicationContext());

        outputDAO = outputDAO.get(getActivity().getApplicationContext());

        outputsResultReceiver = new OutputsResultReceiver(new Handler());

        refreshHandler = new Handler();

        runnable = new OutputsStatusRefreshRunnable(refreshInterval);

        refreshHandler.postDelayed(runnable, 0000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_outputs, container, false);

        setupImageViews(view);
        setupButtonViews(view);
        setupNameViews(view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.outputs_swipe_layout);

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
            Intent intent = OutputIntentService.newRefreshIntent(getContext());
            intent.putExtra(Constants.RECEIVER, outputsResultReceiver);
            getActivity().startService(intent);
        } else {
            refreshLayout.setRefreshing(false);

            if (getView() != null)
                Snackbar.make(getView(), getString(R.string.network_required_msg), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        List<Output> outputList = outputDAO.findAll();

        updatedImageViews(outputList);
        updatedButtonViews(outputList);
        updatedNameViews(outputList);
    }

    private void setupImageViews(View v) {
        out0Image = (ImageView) v.findViewById(R.id.output_0_image_view);
        out1Image = (ImageView) v.findViewById(R.id.output_1_image_view);
        out2Image = (ImageView) v.findViewById(R.id.output_2_image_view);
        out3Image = (ImageView) v.findViewById(R.id.output_3_image_view);
        out4Image = (ImageView) v.findViewById(R.id.output_4_image_view);
        out5Image = (ImageView) v.findViewById(R.id.output_5_image_view);
        out6Image = (ImageView) v.findViewById(R.id.output_6_image_view);
        out7Image = (ImageView) v.findViewById(R.id.output_7_image_view);
    }

    private void setupNameViews(View v) {
        out0NameText = (TextView) v.findViewById(R.id.output_0_name_view);
        out1NameText = (TextView) v.findViewById(R.id.output_1_name_view);
        out2NameText = (TextView) v.findViewById(R.id.output_2_name_view);
        out3NameText = (TextView) v.findViewById(R.id.output_3_name_view);
        out4NameText = (TextView) v.findViewById(R.id.output_4_name_view);
        out5NameText = (TextView) v.findViewById(R.id.output_5_name_view);
        out6NameText = (TextView) v.findViewById(R.id.output_6_name_view);
        out7NameText = (TextView) v.findViewById(R.id.output_7_name_view);
    }

    private void setupButtonViews(View v) {
        out0Btn = (ToggleButton) v.findViewById(R.id.output_0_button);
        out1Btn = (ToggleButton) v.findViewById(R.id.output_1_button);
        out2Btn = (ToggleButton) v.findViewById(R.id.output_2_button);
        out3Btn = (ToggleButton) v.findViewById(R.id.output_3_button);
        out4Btn = (ToggleButton) v.findViewById(R.id.output_4_button);
        out5Btn = (ToggleButton) v.findViewById(R.id.output_5_button);
        out6Btn = (ToggleButton) v.findViewById(R.id.output_6_button);
        out7Btn = (ToggleButton) v.findViewById(R.id.output_7_button);

        setupButtonEvents();
    }

    private void setupButtonEvents() {

        View.OnClickListener listener = buildListener();

        out0Btn.setOnClickListener(listener);
        out1Btn.setOnClickListener(listener);
        out2Btn.setOnClickListener(listener);
        out3Btn.setOnClickListener(listener);
        out4Btn.setOnClickListener(listener);
        out5Btn.setOnClickListener(listener);
        out6Btn.setOnClickListener(listener);
        out7Btn.setOnClickListener(listener);
    }

    private View.OnClickListener buildListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = ((ToggleButton) v).isChecked();

                switch (v.getId()) {
                    case R.id.output_0_button:
                        controlOutput(0, isChecked);
                        break;
                    case R.id.output_1_button:
                        controlOutput(1, isChecked);
                        break;
                    case R.id.output_2_button:
                        controlOutput(2, isChecked);
                        break;
                    case R.id.output_3_button:
                        controlOutput(3, isChecked);
                        break;
                    case R.id.output_4_button:
                        controlOutput(4, isChecked);
                        break;
                    case R.id.output_5_button:
                        controlOutput(5, isChecked);
                        break;
                    case R.id.output_6_button:
                        controlOutput(6, isChecked);
                        break;
                    case R.id.output_7_button:
                        controlOutput(7, isChecked);
                        break;
                }
            }
        };
    }

    private void controlOutput(int no, boolean value) {
        if (networkService.isNetworkAvailableAndConnected()) {
            Intent intent = OutputIntentService.newOutputControlIntent(getContext(), no, value);
            intent.putExtra(Constants.RECEIVER, outputsResultReceiver);
            getActivity().startService(intent);
        } else {
            if (getView() != null)
                Snackbar.make(getView(), getString(R.string.network_required_msg), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updatedImageViews(List<Output> outputList) {
        out0Image.setImageDrawable(getDrawableForStatus(outputList.get(0).getOutputStatus()));
        out1Image.setImageDrawable(getDrawableForStatus(outputList.get(1).getOutputStatus()));
        out2Image.setImageDrawable(getDrawableForStatus(outputList.get(2).getOutputStatus()));
        out3Image.setImageDrawable(getDrawableForStatus(outputList.get(3).getOutputStatus()));
        out4Image.setImageDrawable(getDrawableForStatus(outputList.get(4).getOutputStatus()));
        out5Image.setImageDrawable(getDrawableForStatus(outputList.get(5).getOutputStatus()));
        out6Image.setImageDrawable(getDrawableForStatus(outputList.get(6).getOutputStatus()));
        out7Image.setImageDrawable(getDrawableForStatus(outputList.get(7).getOutputStatus()));
    }

    private void updatedNameViews(List<Output> outputList) {
        out0NameText.setText(outputList.get(0).getName());
        out1NameText.setText(outputList.get(1).getName());
        out2NameText.setText(outputList.get(2).getName());
        out3NameText.setText(outputList.get(3).getName());
        out4NameText.setText(outputList.get(4).getName());
        out5NameText.setText(outputList.get(5).getName());
        out6NameText.setText(outputList.get(6).getName());
        out7NameText.setText(outputList.get(7).getName());
    }

    private void updatedButtonViews(List<Output> outputList) {
        out0Btn.setChecked(outputList.get(0).getBoolStatus());
        out1Btn.setChecked(outputList.get(1).getBoolStatus());
        out2Btn.setChecked(outputList.get(2).getBoolStatus());
        out3Btn.setChecked(outputList.get(3).getBoolStatus());
        out4Btn.setChecked(outputList.get(4).getBoolStatus());
        out5Btn.setChecked(outputList.get(5).getBoolStatus());
        out6Btn.setChecked(outputList.get(6).getBoolStatus());
        out7Btn.setChecked(outputList.get(7).getBoolStatus());

        out0Btn.setEnabled(outputList.get(0).getOutputStatus() > -1);
        out1Btn.setEnabled(outputList.get(1).getOutputStatus() > -1);
        out2Btn.setEnabled(outputList.get(2).getOutputStatus() > -1);
        out3Btn.setEnabled(outputList.get(3).getOutputStatus() > -1);
        out4Btn.setEnabled(outputList.get(4).getOutputStatus() > -1);
        out5Btn.setEnabled(outputList.get(5).getOutputStatus() > -1);
        out6Btn.setEnabled(outputList.get(6).getOutputStatus() > -1);
        out7Btn.setEnabled(outputList.get(7).getOutputStatus() > -1);
    }

    private Drawable getDrawableForStatus(int status) {
        switch (status) {
            case 0:
                return getResources().getDrawable(R.drawable.ic_led_blue);
            case 1:
                return getResources().getDrawable(R.drawable.ic_led_red);
            default:
                return getResources().getDrawable(R.drawable.ic_led_grey);
        }
    }

    @SuppressLint("ParcelCreator")
    class OutputsResultReceiver extends ResultReceiver {
        OutputsResultReceiver(Handler handler) {
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

    class OutputsStatusRefreshRunnable implements Runnable {
        private int refreshInterval;

        OutputsStatusRefreshRunnable(int refreshInterval) {
            this.refreshInterval = refreshInterval;
        }

        public void run() {
            refreshHandler.postDelayed(this, refreshInterval);
            refreshFromServer();
        }
    }

}
