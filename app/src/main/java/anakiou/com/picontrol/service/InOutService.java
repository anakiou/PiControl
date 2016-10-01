package anakiou.com.picontrol.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import anakiou.com.picontrol.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InOutService {

    private Context context;

    private static InOutService inOutService;

    public static InOutService get(Context context) {
        if (inOutService == null) {
            inOutService = new InOutService(context);

        }
        return inOutService;
    }

    private InOutService(Context context){
         this.context = context;
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
