package com.jc.flora.apps.component.cache.delegate;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * 双缓存的静态变量缓存设计
 * Created by Shijincheng on 2018/8/8.
 */

public class Cacher {

    private static final String CACHE_TITLE = "cache";

    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor sEditor;

    /**
     * 在Application的onCreate()方法中调用
     * @param context
     */
    public static void init(Context context) {
        if(sPreferences == null){
            sPreferences = context.getApplicationContext().getSharedPreferences(CACHE_TITLE, Context.MODE_PRIVATE);
        }
        if(sEditor == null){
            sEditor = sPreferences.edit();
        }
    }

    public static SharedPreferences getSp() {
        return sPreferences;
    }

    public static SharedPreferences.Editor edit(){
        return sEditor;
    }

    public static String objectToString(Object obj){
        return new Gson().toJson(obj);
    }

    public static <T> T stringToObject(String str, Class<T> clazz) {
        return new Gson().fromJson(str, clazz);
    }

    /**
     * 在应用退出时调用
     */
    public static void clear() {
        if (sEditor != null) {
            sEditor.clear();
            sEditor.apply();
        }
    }

}
