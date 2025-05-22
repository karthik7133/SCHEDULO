package com.carcar.myapplication;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Debug;

public class MyApplication extends Application {
    public void onCreate(){
        super.onCreate();


        if(isDebuggable()){
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private boolean isDebuggable(){
        return
                (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0 || Debug.isDebuggerConnected();
    }
}
