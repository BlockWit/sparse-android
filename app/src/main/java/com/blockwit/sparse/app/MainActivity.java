package com.blockwit.sparse.app;

import android.Manifest;
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

    private final int REQUEST_CODE_SMS_PERMISSION = 1;

    private final int REQUEST_READ_PHONE_STATE = 2;

    private final int REQUEST_READ_PHONE_NUMBERS = 3;

    private final int REQUEST_INTERNET = 4;

    private final int REQUEST_SEND_SMS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestSmsPermission();
    }

    private void requestSmsPermission() {
        Log.v(TAG, "Request permission");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQUEST_CODE_SMS_PERMISSION
            );
            Log.v(TAG, "Permission requested");
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
            Log.v(TAG, "Permission requested");
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                    REQUEST_READ_PHONE_NUMBERS);
            Log.v(TAG, "Permission requested");
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    REQUEST_INTERNET);
            Log.v(TAG, "Permission requested");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
            Log.v(TAG, "Permission requested");
        }

    }

    public void onClickAdd(View view) {
        Log.d(TAG, "Add button clicked");
        EditText myEditText =  (EditText) findViewById(R.id.editTextPhone);
        String number = myEditText.getText().toString();
        Log.d(TAG, "Text from editTextPhone " + number);

        SmsManager smsManager = SmsManager.getDefault();
        String reg_msg_body = SMSMonitor.REG_CODE_WORD + " " + number;
        try {
            smsManager.sendTextMessage(number, null, reg_msg_body, null, null);
        } catch (Throwable t) {
            Log.d(TAG, t.getMessage());
        }

    }

}