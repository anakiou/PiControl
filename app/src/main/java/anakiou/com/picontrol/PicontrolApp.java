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
