package anakiou.com.picontrol.service;


import java.util.List;

import anakiou.com.picontrol.domain.EventLog;
import anakiou.com.picontrol.domain.Input;
import anakiou.com.picontrol.domain.Output;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PiserverApi {

    //INPUTS

    @GET("/api/input")
    Call<List<Input>> getInputs();

    @GET("/api/input/names")
    Call<List<String>> getInputNames();

    @GET("/api/input/{no}")
    Call<Integer> getSingleInputStatus(@Path("no") int inputNo);

    @GET("/api/input/status")
    Call<List<Integer>> getAllInputsStatus();

    @POST("/api/input/{no}")
    Call<String> setInputName(@Path("no") int inputNo, @Body String name);


    //OUTPUTS

    @GET("/api/output")
    Call<List<Output>> getOutputs();

    @GET("/api/output/names")
    Call<List<String>> getOutputNames();

    @GET("/api/output/{no}")
    Call<Integer> getSingleOutputStatus(@Path("no") int outputNo);

    @GET("/api/output/status")
    Call<List<Integer>> getAllOutputsStatus();

    @POST("/api/output/{no}")
    Call<String> setOutputName(@Path("no") int outputNo, @Body String name);

    @POST("/api/output/{no}/{value}")
    Call<Integer> setControl(@Path("no") int outputNo, @Path("value") boolean value);

    @POST("/api/output/all")
    Call<List<Integer>> setAllControls(@Body boolean[] values);

    // EVENT LOG

    @GET("/api/events/count")
    Call<Integer> eventCount();

    @GET("/api/events")
    Call<List<EventLog>> getAllEvents();

    @GET("/api/events/{no}")
    Call<List<EventLog>> getLatestEvents(@Path("no") int no);

}
