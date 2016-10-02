package anakiou.com.picontrol.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import anakiou.com.picontrol.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InOutService {

    private static final String TAG = "InOutService";

    private Context context;

    private static InOutService inOutService;

    public static InOutService get(Context context) {
        if (inOutService == null) {
            inOutService = new InOutService(context);

        }
        return inOutService;
    }

    private InOutService(Context context) {
        this.context = context;
    }

    public int controlOutput(int outNo, boolean value) {
        try {
            PiserverApi api = prepareApi();

            Response<Integer> response = api.setOutputValue(outNo, value).execute();

            if (response.isSuccessful() && response.code() == Constants.OK_200) {
                return response.body();
            }

            Log.e(TAG, "Error setting OUTPUT, Code => " + response.code() + " , Body => " + response.body());

        } catch (IOException ex) {
            Log.e(TAG, "Error while setting OUTPUT", ex);
        }

        return -1;
    }

    public int getOutputStatus(int outNo) {
        try {
            PiserverApi api = prepareApi();

            Response<Integer> response = api.getOutputStatus(outNo).execute();

            if (response.isSuccessful() && response.code() == Constants.OK_200) {
                return response.body();
            }

            Log.e(TAG, "Error getting OUTPUT status, Code => " + response.code() + " , Body => " + response.body());

        } catch (IOException ex) {
            Log.e(TAG, "Error while getting OUTPUT status", ex);
        }

        return -1;
    }

    public int getInputStatus(int inNo) {
        try {
            PiserverApi api = prepareApi();

            Response<Integer> response = api.getInputStatus(inNo).execute();

            if (response.isSuccessful() && response.code() == Constants.OK_200) {
                return response.body();
            }

            Log.e(TAG, "Error getting INPUT status, Code => " + response.code() + " , Body => " + response.body());

        } catch (IOException ex) {
            Log.e(TAG, "Error while getting INPUT status", ex);
        }

        return -1;
    }

    private PiserverApi prepareApi() {

        final Gson gson = new GsonBuilder().create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(PiserverApi.class);
    }
}
