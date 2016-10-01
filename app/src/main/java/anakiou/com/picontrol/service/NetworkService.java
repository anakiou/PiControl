package anakiou.com.picontrol.service;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkService {

    private static NetworkService networkService;

    private Context context;

    public static NetworkService get(Context context) {

        if (networkService == null) {
            networkService = new NetworkService(context);
        }

        return networkService;
    }

    private NetworkService(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailableAndConnected() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
    }

    private boolean isOnWifi() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return !cm.isActiveNetworkMetered();
    }
}
