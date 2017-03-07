package com.ampme.challenge;

import android.app.Application;

import com.facebook.FacebookSdk;

public class ChallengeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
