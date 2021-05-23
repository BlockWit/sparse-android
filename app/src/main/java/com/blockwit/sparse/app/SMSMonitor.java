package com.blockwit.sparse.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSMonitor extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    public SMSMonitor() {
        Log.i("SMSMONITOR", "===> CONSTR ===> MOONITOR");
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("SMSMONITOR", "===> 7");
        if (intent != null && intent.getAction() != null &&
                Telephony.Sms.Intents.SMS_RECEIVED_ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Log.i("SMSMONITOR", "===> 8");
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            Log.i("SMSMONITOR", "===> 9");
            String sms_from = messages[0].getDisplayOriginatingAddress();
            Log.i("SMSMONITOR", "===> 10");
            if (sms_from.equalsIgnoreCase("Tinkoff")) {
                Log.i("SMSMONITOR", "===> 11");
                StringBuilder bodyText = new StringBuilder();
                for (int i = 0; i < messages.length; i++) {
                    bodyText.append(messages[i].getMessageBody());
                }
                Log.i("SMSMONITOR", "===> 12");
                String body = bodyText.toString();
                Intent mIntent = new Intent(context, SMSService.class);
                mIntent.putExtra("sms_body", body);
                Log.i("SMSMONITOR", "===> 13");
                context.startService(mIntent);

                //abortBroadcast();
            }
        }
    }

}
