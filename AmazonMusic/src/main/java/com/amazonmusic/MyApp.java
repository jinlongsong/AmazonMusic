package com.amazonmusic;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class MyApp extends Application {
    private String TAG = "TAG";
	private String currentsongname;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyApp created");
    }

    public String getCurrentSongName() {
        return currentsongname;
    }

    public void setCurrentSongName(String currentsongname) {
        this.currentsongname = currentsongname;
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "MyApp onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "MyApp onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行（回收内存）
        Log.d(TAG, "MyApp onTrimMemory");// HOME键退出应用程序、长按MENU键，打开Recent TASK都会执行
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}
