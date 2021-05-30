package com.blockwit.sparse.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

;
    private String TAG = MainActivity.class.getSimpleName();

    private final int REQUEST_SEND_SMS = 5;

    private int PERMISSION_ALL = 1;

    public static final String CURRENT_SERVER = "currentServer";
    public static final String SERVER_DETAILS = "SERVER_DETAILS";
    public static final String MIN_SERVER = "minServer";
    public static final String MAX_SERVER = "maxServer";

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
        initServerRadioButton();
        requestSmsPermission();
    }

    void initServerRadioButton() {


        Context context = this.getApplicationContext();
        SharedPreferences sereverDetails = context.getSharedPreferences(SERVER_DETAILS,
                MODE_PRIVATE);
        String currentServer = sereverDetails.getString(CURRENT_SERVER, MIN_SERVER);
        RadioGroup serverRadioGroup = findViewById(R.id.serverRadioGroup);



        if (currentServer.equals(MAX_SERVER)) {
            serverRadioGroup.check(R.id.radioMaxServer);
        } else {
            serverRadioGroup.check(R.id.radioMinServer);
        }
    }


    public void onServerRadioButtonClick(View v) {
        Context context = this.getApplicationContext();
        SharedPreferences sereverDetails = context.getSharedPreferences(SERVER_DETAILS,
                MODE_PRIVATE);
        RadioButton rb = (RadioButton)v;
        boolean checked = rb.isChecked();
        switch (rb.getId()) {
            case R.id.radioMaxServer:
                if (checked) {
                    sereverDetails.edit().putString(CURRENT_SERVER, MAX_SERVER).apply();
                    Log.i(TAG, "Max server have chosen");
                }
                break;
            case R.id.radioMinServer:
                if (checked) {
                    sereverDetails.edit().putString(CURRENT_SERVER, MIN_SERVER).apply();
                    Log.i(TAG, "Min server have chosen");
                }
                break;
            default:
                Log.i(TAG, "Error in chosing server");
        }
    }


//    private View.OnClickListener serverRadioButtonClickListener = new View.OnClickListener() {
//        @Override
//
//    };


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