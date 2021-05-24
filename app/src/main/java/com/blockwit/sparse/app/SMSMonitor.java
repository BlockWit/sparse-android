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
            .baseUrl("https://sparse.blockwit.io/api/v1/sms/")
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
//            Intent mIntent = new Intent(context, SMSService.class);
//            mIntent.putExtra("sms_body", body);
//            Log.i("SMSMONITOR", "===> 13");
//            context.startService(mIntent);
            sParseAPI.save(new SMSToProcess(from, body)).enqueue(new Callback<Object>() {
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

//        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.getLine1Number();
//        Log.v(TAG, "to: " + telephonyManager.getLine1Number());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            Bundle bundle = intent.getExtras();
//            int sub = bundle.getInt("subscription", -1);
//            SubscriptionManager manager = SubscriptionManager.from(context);
//            @SuppressLint("MissingPermission") SubscriptionInfo subnfo = manager.getActiveSubscriptionInfo(sub);//this requires READ_PHONE_STATE permission
//            Log.v(TAG, "to: " + subnfo.getNumber());
//        }


//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            int slot = -1;
//            Set<String> keySet = bundle.keySet();
//            for (String key : keySet) {
//                if (key.equals("phone")) {
//                    slot = bundle.getInt("phone", -1);
//                }//in api 29
//                else if (key.equals("slot")) {
//                    slot = bundle.getInt("slot", -1);
//                } else if (key.equals("slotId")) {
//                    slot = bundle.getInt("slotId", -1);
//                } else if (key.equals("slot_id")) {
//                    slot = bundle.getInt("slot_id", -1);
//                } else if (key.equals("slotIdx")) {
//                    slot = bundle.getInt("slotIdx", -1);
//                } else if (key.equals("simId")) {
//                    slot = bundle.getInt("simId", -1);
//                } else if (key.equals("simSlot")) {
//                    slot = bundle.getInt("simSlot", -1);
//                } else if (key.equals("simnum")) {
//                    slot = bundle.getInt("simnum", -1);
//                }
//            }
//            Log.v(TAG, slot + "");
//        }

//
//        Log.i("SMSMONITOR", "===> 7");
//        Log.i("SMSMONITOR", "===> 7");
//        Log.i("SMSMONITOR", "===> 7");
//        if (intent != null && intent.getAction() != null &&
//                Telephony.Sms.Intents.SMS_RECEIVED_ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
//            Log.i("SMSMONITOR", "===> 8");
//            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
//            SmsMessage[] messages = new SmsMessage[pduArray.length];
//            for (int i = 0; i < pduArray.length; i++) {
//                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
//            }
//            Log.i("SMSMONITOR", "===> 9");
//            String sms_from = messages[0].getDisplayOriginatingAddress();
//            Log.i("SMSMONITOR", "===> 10");
//            if (sms_from.equalsIgnoreCase("Tinkoff")) {
//                Log.i("SMSMONITOR", "===> 11");
//                StringBuilder bodyText = new StringBuilder();
//                for (int i = 0; i < messages.length; i++) {
//                    bodyText.append(messages[i].getMessageBody());
//                }
//                Log.i("SMSMONITOR", "===> 12");
//                String body = bodyText.toString();
//                String body1 = bodyText.toString();
////                Intent mIntent = new Intent(context, SMSService.class);
////                mIntent.putExtra("sms_body", body);
////                Log.i("SMSMONITOR", "===> 13");
////                context.startService(mIntent);
//
//                //abortBroadcast();
//            }
//        }
    }

}
