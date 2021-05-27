package com.blockwit.sparse.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SMSMonitor extends BroadcastReceiver {

    private String TAG = SMSMonitor.class.getSimpleName();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sparse.blockwit.io/api/v1/msgs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SParseAPI sParseAPI = retrofit.create(SParseAPI.class);

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null || !intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
            return;

        SmsMessage[] extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsMessage : extractMessages) {
            String body = smsMessage.getDisplayMessageBody();
            Log.v(TAG, "message: " + body);
            String from = smsMessage.getDisplayOriginatingAddress();
            Log.v(TAG, "from: " + smsMessage.getDisplayOriginatingAddress());
            String to = "!!!";
            sParseAPI.save(new MessageDTO(MessageProviderType.SMS, System.currentTimeMillis(), from, to, body)).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Log.i(TAG, "success: " + response);
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.i(TAG, "fail: " + t.getMessage());
                }
            });
        }
    }

}
