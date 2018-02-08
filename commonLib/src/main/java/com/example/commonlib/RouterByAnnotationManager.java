package com.example.commonlib;


import java.util.HashMap;


/**
 * 路由表
 * Created by lishaojie on 2018/1/11.
 */

public class RouterByAnnotationManager {
    public static HashMap<String, String> routers = new HashMap<>();
    private static RouterByAnnotationManager instance = new RouterByAnnotationManager();

    public static RouterByAnnotationManager getInstance() {
        return instance;
    }


    public void addRouter(String key, String activity) {
        routers.put(key, activity);
    }

    public String getRouter(String key) {
        return routers.get(key);
    }

}
