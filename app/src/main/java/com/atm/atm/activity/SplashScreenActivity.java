package com.atm.atm.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Intent intent;
        if (AccessToken.getCurrentAccessToken()!=null) {
            intent  = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
