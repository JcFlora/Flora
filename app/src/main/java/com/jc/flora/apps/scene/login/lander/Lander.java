package com.jc.flora.apps.scene.login.lander;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 登录器外观类，提供全局配置
 * Created by shijincheng on 2020/6/29.
 */
public final class Lander {

    /**
     * 初始化，实际项目中在Application的onCreate()方法中调用
     * @param loginActivityClass
     * @param loginInfoDataSource
     */
    public static void init(Class<? extends Activity> loginActivityClass,
                            LoginInfoDataSource loginInfoDataSource){
        LoginStatusSyncExecutor.setLoginActivityClass(loginActivityClass);
        LoginStatusSyncExecutor.setLoginInfoDataSource(loginInfoDataSource);
    }

    /**
     * 创建登录状态同步器
     * @param activity
     * @param tag
     * @return
     */
    public static LoginStatusSyncExecutor createExecutor(AppCompatActivity activity, String tag){
        return LoginStatusSyncExecutor.createExecutor(activity, tag);
    }

    /**
     * 创建登录状态同步器
     * @param fragment
     * @param tag
     * @return
     */
    public static LoginStatusSyncExecutor createExecutor(Fragment fragment, String tag){
        return LoginStatusSyncExecutor.createExecutor(fragment, tag);
    }

    /**
     * 登录页面登录成功后调用此方法
     *
     * @param activity
     */
    public static void loginSuccess(Activity activity) {
        LoginStatusSyncExecutor.loginSuccess(activity);
    }

    /**
     * 登录页面登录取消后调用此方法
     *
     * @param activity
     */
    public static void loginCancel(Activity activity) {
        LoginStatusSyncExecutor.loginCancel(activity);
    }

    /**
     * 登录页面onBackPressed调用此方法
     * @param activity
     */
    public static void onBackPressed(AppCompatActivity activity) {
        //重写Activity的onBackPressed方法，如果页面内部的Fragment有回退栈，则返回时先回退Fragment，如果没有，触发登录取消的回调
        if(!activity.getSupportFragmentManager().popBackStackImmediate()){
            LoginStatusSyncExecutor.loginCancel(activity);
        }
    }

}