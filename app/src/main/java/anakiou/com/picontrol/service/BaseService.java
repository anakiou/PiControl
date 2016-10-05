package anakiou.com.picontrol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import anakiou.com.picontrol.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseService {

    public PiserverApi prepareApi() {

        final Gson gson = new GsonBuilder().create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(PiserverApi.class);
    }

    public boolean isResponseOk(Response<?> response) {
        return response.isSuccessful() && response.code() == Constants.OK_200;
    }
}
