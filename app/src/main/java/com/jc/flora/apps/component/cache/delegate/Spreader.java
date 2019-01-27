package com.jc.flora.apps.component.cache.delegate;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.File;

/**
 * Sp的简单封装
 * Created by Shijincheng on 2018/8/8.
 */

public class Spreader {

    private static final String SP_TITLE = "sp";

    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor sEditor;

    /**
     * 在Application的onCreate()方法中调用
     * @param context
     */
    public static void init(Context context) {
        if(sPreferences == null){
            sPreferences = context.getApplicationContext().getSharedPreferences(SP_TITLE, Context.MODE_PRIVATE);
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
     * 在清理缓存时调用
     */
    public static void clear() {
        if (sEditor != null) {
            sEditor.clear();
            sEditor.apply();
        }
    }

    /**
     * 在清理缓存时调用
     */
    public static void clearAll(Context context) {
        File file = new File("/data/data/"
                + context.getApplicationContext().getPackageName() + "/shared_prefs/" + SP_TITLE + ".xml");
        file.delete();
    }

}
