package com.atm.atm;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Date;

/**
 * Created by denis on 10.09.16.
 */
public class AtmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
