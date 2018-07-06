package com.martin.animationstudy;

import android.app.Application;

import timber.log.Timber;

/**
 * @author : martin
 * @date : 2018/7/4
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
