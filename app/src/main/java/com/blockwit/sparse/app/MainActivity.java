package com.blockwit.sparse.app;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    SMSMonitor smsMonitor = new SMSMonitor();

    public MainActivity() {
        Log.i("SMSMONITOR", "===> CONSTR ===> ACTIVITY");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("SMSMONITOR"
                , "===> 13 ===>");

//        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        registerReceiver(smsMonitor, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(smsMonitor);
    }

}