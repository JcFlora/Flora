package com.jc.flora.apps.scene.pray.elete;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * 置灰器外观类，提供全局配置
 * Created by shijincheng on 2020/9/18.
 */
public final class Elete {

    /**
     * 初始化，实际项目中在Application的onCreate()方法中调用
     * @param prayInfoDataSource
     */
    public static void init(PrayInfoDataSource prayInfoDataSource){
        PrayCmdExecutor.setPrayInfoDataSource(prayInfoDataSource);
    }

    /**
     * 创建置灰器
     * @param activity
     * @param tag
     * @return
     */
    public static PrayCmdExecutor createExecutor(AppCompatActivity activity, String tag){
        return PrayCmdExecutor.createExecutor(activity, tag);
    }

    /**
     * 创建置灰器
     * @param fragment
     * @param tag
     * @return
     */
    public static PrayCmdExecutor createExecutor(Fragment fragment, String tag){
        return PrayCmdExecutor.createExecutor(fragment, tag);
    }

    /**
     * 全局置灰时调用此方法
     *
     * @param activity
     */
    public static void pray(Activity activity) {
        PrayCmdExecutor.pray(activity);
    }

    /**
     * 全局还原时调用此方法
     *
     * @param activity
     */
    public static void clear(Activity activity) {
        PrayCmdExecutor.clear(activity);
    }

}