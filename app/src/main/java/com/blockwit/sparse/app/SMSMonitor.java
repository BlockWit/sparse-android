package com.blockwit.sparse.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SMSMonitor extends BroadcastReceiver {

    public static final String REG_CODE_WORD = "COM_BLOCKWIT_SPARCE_APP_REG_CODE_WORD";
    private String TAG = SMSMonitor.class.getSimpleName();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sparse.blockwit.io/api/v1/msgs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SParseAPI sParseAPI = retrofit.create(SParseAPI.class);

    /** @// TODO: 27.05.2021 save to storage */
    static Map<String, String> ICCIDtoPhoneNumber = new HashMap<>();

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null || !intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
            return;
        Bundle bundle = intent.getExtras();
        int sub = bundle.getInt("subscription", -1);
        SubscriptionManager manager = SubscriptionManager.from(context);
        SubscriptionInfo info = manager.getActiveSubscriptionInfo(sub);
        String iccid = info.getIccId();
        if (iccid.isEmpty()) {
            Log.e(TAG, "Cant get iccid");
        } else {
            SmsMessage[] extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (SmsMessage smsMessage : extractMessages) {
                String body = smsMessage.getDisplayMessageBody();
                Log.v(TAG, "message: " + body);
                String from = smsMessage.getDisplayOriginatingAddress();
                Log.v(TAG, "from: " + smsMessage.getDisplayOriginatingAddress());


                if(body.startsWith(REG_CODE_WORD)) {
                    /* registration phone number*/
                    String destinationNumber = body.substring(REG_CODE_WORD.length());
                    ICCIDtoPhoneNumber.put(iccid, destinationNumber);
                } else {
                    String to = ICCIDtoPhoneNumber.get(iccid);
                    if (to == null) {
                        to = "";
                    }
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
    }

}
