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

    @GET("/api/input/status")
    Call<List<Integer>> getAllInputsStatus();

    @POST("/api/input/{no}")
    Call<String> setInputName(@Path("no") int inputNo, @Body String name);


    //OUTPUTS

    @GET("/api/output")
    Call<List<Output>> getOutputs();

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
