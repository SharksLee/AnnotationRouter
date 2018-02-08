package com.example.routerlib;

import android.util.Log;

import com.example.commonlib.RouterInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * 将路由数据存入{@link com.example.commonlib.RouterByAnnotationManager}
 * Created by lishaojie on 2018/2/7.
 */

public class RouterFinder {

    public RouterFinder() {
        throw new AssertionError("No .instances");
    }


    private static Map<String, RouterInjector> FINDER_MAP = new HashMap<>();

    /**
     * 获取目标类
     *
     * @param className
     */
    public static void bind(String  className) {
        try {
            Log.e("bind",className);
            RouterInjector injector = FINDER_MAP.get(className);
            if (injector == null) {
                Class<?> finderClass = Class.forName(className);
                injector = (RouterInjector) finderClass.newInstance();
                FINDER_MAP.put(className, injector);
            }
            injector.injectRouter();
        } catch (Exception e) {
        }
    }

}
