package com.blockwit.sparse.app;

import retrofit2.http.POST;

public interface SParseAPI {

    @POST("save")
    void save(SMSToProcess smsToProcess);

}
