package com.example.administrator.annatationrouter;

import android.app.Application;

import com.example.routerlib.Router;

/**
 * Created by lishaojie on 2018/2/8.
 */

public class RouterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        Router.init(this);
    }
}
