package anakiou.com.picontrol.service;


import java.util.List;

import anakiou.com.picontrol.domain.Input;
import anakiou.com.picontrol.domain.Output;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PiserverApi {

    //INPUTS

    @GET("/input")
    Call<List<Input>> loadInputs();

    @GET("/input/names")
    Call<List<String>> loadInputNames();

    @GET("/input/{no}")
    Call<Integer> getInputStatus(@Path("no") int inputNo);

    @POST("/input/{no}")
    Call<String> updateInputName(@Path("no") int inputNo, @Body String name);

    //OUTPUTS

    @GET("/output")
    Call<List<Output>> loadOutputs();

    @GET("/output/names")
    Call<List<String>> loadOutputNames();

    @GET("/output/{no}")
    Call<Integer> getOutputStatus(@Path("no") int outputNo);

    @POST("/output/{no}")
    Call<String> updateOutputName(@Path("no") int outputNo, @Body String name);

    @POST("/output/{no}/{value}")
    Call<Integer> setOutputValue(@Path("no") int outputNo, @Path("value") boolean value);
}
