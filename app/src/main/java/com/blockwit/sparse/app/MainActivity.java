package com.blockwit.sparse.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private final int REQUEST_SEND_SMS = 5;

    private int PERMISSION_ALL = 1;

    String[] PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.INTERNET,
            Manifest.permission.SEND_SMS
    };

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestSmsPermission();
    }

    private void requestSmsPermission() {
        Log.v(TAG, "Request permission");
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            Log.v(TAG, "Permissions requested");
        }

    }

    public void onClickAdd(View view) {
        Log.d(TAG, "Add button clicked");
        EditText myEditText =  (EditText) findViewById(R.id.editTextPhone);
        String number = myEditText.getText().toString();
        Log.d(TAG, "Text from editTextPhone " + number);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
            Log.v(TAG, "Permission requested");
        }
        SmsManager smsManager = SmsManager.getDefault();
        String reg_msg_body = SMSMonitor.REG_CODE_WORD + " " + number;
        try {
            smsManager.sendTextMessage(number, null, reg_msg_body, null, null);
        } catch (Throwable t) {
            Log.d(TAG, t.getMessage());
        }

    }

}