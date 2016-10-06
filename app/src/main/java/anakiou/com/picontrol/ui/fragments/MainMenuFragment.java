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
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import anakiou.com.picontrol.R;
import anakiou.com.picontrol.service.InputIntentService;
import anakiou.com.picontrol.service.NetworkService;
import anakiou.com.picontrol.service.OutputIntentService;
import anakiou.com.picontrol.ui.activities.EditNamesActivity;
import anakiou.com.picontrol.ui.activities.InputsActivity;
import anakiou.com.picontrol.ui.activities.OutputsActivity;
import anakiou.com.picontrol.util.Constants;

public class MainMenuFragment extends Fragment {

    private MainMenuRefreshResultReceiver mainMenuRefreshResultReceiver;

    private SwipeRefreshLayout refreshLayout;

    private NetworkService networkService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        networkService = NetworkService.get(getActivity().getApplicationContext());

        mainMenuRefreshResultReceiver = new MainMenuRefreshResultReceiver(new Handler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        setupInputs(view);

        setupOutputs(view);

        setupEditNames(view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_menu_swipe_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Intent inputIntent = InputIntentService.newRefreshIntent(getContext());
                Intent outputIntent = OutputIntentService.newRefreshIntent(getContext());

                inputIntent.putExtra(Constants.RECEIVER, mainMenuRefreshResultReceiver);
                outputIntent.putExtra(Constants.RECEIVER, mainMenuRefreshResultReceiver);

                getActivity().startService(inputIntent);
                getActivity().startService(outputIntent);
            }
        });

        return view;
    }

    private void setupInputs(View view) {

        ImageView inputsImage = (ImageView) view.findViewById(R.id.inputs_image);

        inputsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkService.isNetworkAvailableAndConnected()) {
                    Intent intent = InputsActivity.newIntent(getActivity());
                    startActivity(intent);
                } else {
                    noNetwork();
                }
            }
        });

    }

    private void setupOutputs(View view) {

        ImageView outputsImage = (ImageView) view.findViewById(R.id.outputs_image);

        outputsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkService.isNetworkAvailableAndConnected()) {
                    Intent intent = OutputsActivity.newIntent(getActivity());
                    startActivity(intent);
                } else {
                    noNetwork();
                }
            }
        });
    }

    private void setupEditNames(View view) {

        ImageView editNamesImage = (ImageView) view.findViewById(R.id.edit_names_image);

        editNamesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (networkService.isNetworkAvailableAndConnected()) {
                    Intent intent = EditNamesActivity.newIntent(getActivity());
                    startActivity(intent);
                } else {
                    noNetwork();
                }
            }
        });
    }

    private void noNetwork() {
        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.network_required_msg), Snackbar.LENGTH_LONG).show();
        }
    }

    @SuppressLint("ParcelCreator")
    class MainMenuRefreshResultReceiver extends ResultReceiver {
        MainMenuRefreshResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            final String msg = resultData.getString(Constants.RESULT_DATA_KEY);

            refreshLayout.setRefreshing(false);

            if (getView() != null && msg != null) {
                Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
