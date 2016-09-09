package com.isme.shen.slibrary;

import android.app.Application;
import android.content.Context;

/**
 * Created by shen on 2016/9/9.
 */
public class SBaseApplication extends Application {

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return applicationContext;
    }
}
