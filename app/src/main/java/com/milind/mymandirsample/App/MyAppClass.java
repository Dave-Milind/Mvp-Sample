package com.milind.mymandirsample.App;

import android.app.Application;

public class MyAppClass extends Application{
 public static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();

appComponent=DaggerAppComponent.create();

    }
}
