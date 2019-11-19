package com.haiph.oysi;

import android.app.Application;

import com.pushbots.push.Pushbots;

public class NotificationsPush extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Pushbots Library
        Pushbots.sharedInstance().init(this);

    }
}
