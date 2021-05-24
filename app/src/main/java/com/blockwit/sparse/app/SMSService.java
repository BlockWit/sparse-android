package com.blockwit.sparse.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SMSService extends Service {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sparse.blockwit.io/api/v1/sms/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SParseAPI sParseAPI = retrofit.create(SParseAPI.class);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendToServer(String smsBody, String smsFrom) {
        Log.i("SMSMONITOR", "===> 1");
        //sParseAPI.save(new SMSToProcess(smsFrom, smsBody));
        Log.i("SMSMONITOR", "===> 2");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String sms_body = intent.getExtras().getString("sms_body");
//        String sms_from = intent.getExtras().getString("sms_from");
//        sendToServer(sms_body);
        return START_STICKY;
    }

}
