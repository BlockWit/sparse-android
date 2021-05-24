package com.blockwit.sparse.app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SParseAPI {

    @POST("save")
    Call<Object> save(@Body SMSToProcess smsToProcess);

}
