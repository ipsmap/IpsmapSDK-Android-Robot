package com.daoyixun.irobot;

import android.app.Application;
import android.content.Context;


/**
 * author:chen
 * time:2017/10/11
 * desc:
 */
public class App extends Application {

    private static App app;
    public static Context context;

    @Override
    public void onCreate() {
        context = this;
        super.onCreate();
//        IpsMapRobotSDK.init(new IpsMapRobotSDK.Configuration.Builder(context)
//                .debug(true)
//                .build());
    }

}
