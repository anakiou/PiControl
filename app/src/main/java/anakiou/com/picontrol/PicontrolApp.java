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

package anakiou.com.picontrol;

import android.app.Application;
import android.content.Intent;

import anakiou.com.picontrol.service.InputIntentService;
import anakiou.com.picontrol.service.NetworkService;
import anakiou.com.picontrol.service.OutputIntentService;

public class PicontrolApp extends Application {

    public PicontrolApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkService networkService = NetworkService.get(getApplicationContext());

        if (networkService.isNetworkAvailableAndConnected()) {

            Intent inputIntent = InputIntentService.newGetAllIntent(getApplicationContext());

            Intent outputIntent = OutputIntentService.newGetAllIntent(getApplicationContext());

            getApplicationContext().startService(inputIntent);

            getApplicationContext().startService(outputIntent);
        }
    }
}
