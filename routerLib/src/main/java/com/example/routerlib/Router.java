package com.example.routerlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.example.commonlib.ActionConstant;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
/**
 * Created by lishaojie on 2018/2/7.
 */

/**
 * Created by lishaojie on 2018/1/11.
 */

public class Router {
    public static void init(Application application) {
        try {
            Set<String> classNames = ClassUtils.getFileNameByPackageName(application, ActionConstant.SUFFIX);
            for (String className : classNames) {
                RouterFinder.bind(className);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断Intent是否合法可以跳转成功
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        return !context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty();
    }

    public static class Builder {
        private Context mContext;
        private Class<Activity> mClass;
        private ArrayMap<String, Serializable> mArrayMap;

        public Builder(Context context, String aClass) {
            mContext = context;
            try {
                mClass = (Class<Activity>) Class.forName(aClass);
            } catch (ClassNotFoundException e) {

            }
        }

        public Builder addParams(String key, Serializable value) {
            if (mArrayMap == null) {
                mArrayMap = new ArrayMap<>();
            }
            mArrayMap.put(key, value);
            return this;
        }

        public void go() {
            if (mClass == null) {
                Toast.makeText(mContext, "未找到对应的跳转路径", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(mContext, mClass);
            if (mArrayMap != null && !mArrayMap.isEmpty()) {
                Set<String> keys = mArrayMap.keySet();
                for (String key : keys) {
                    intent.putExtra(key, mArrayMap.get(key));
                }
            }
            if (isIntentAvailable(mContext, intent)) {
                mContext.startActivity(intent);
            }
        }


    }
}
