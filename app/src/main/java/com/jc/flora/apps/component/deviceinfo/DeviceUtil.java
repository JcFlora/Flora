package com.jc.flora.apps.component.deviceinfo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by shijincheng on 2017/3/20.
 */
public class DeviceUtil {

    /**
     * 当前系统是否是6.0以上
     * @return
     */
    public static boolean isSystemVersionAfterM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 当前系统是否是7.0以上
     * @return
     */
    public static boolean isSystemVersionAfterN(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 当前系统是否是8.0以上
     * @return
     */
    public static boolean isSystemVersionAfterO(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 获取当前应用内版本号
     * @param ctx
     * @return
     */
    public static int getAppVersionCode(Context ctx){
        PackageInfo packageInfo = getPackageInfo(ctx);
        return packageInfo != null ? packageInfo.versionCode : 0;
    }

    /**
     * 获取当前应用外版本号
     * @param ctx
     * @return
     */
    public static String getAppVersionName(Context ctx){
        PackageInfo packageInfo = getPackageInfo(ctx);
        return packageInfo != null ? packageInfo.versionName : "";
    }

    private static PackageInfo getPackageInfo(Context ctx){
        //获取包管理器
        PackageManager manager = ctx.getPackageManager();
        try {
            //通过当前的包名获取包的信息
            return  manager.getPackageInfo(ctx.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx) {
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param ctx
     * @return
     */
    public static int getScreenHeight(Context ctx) {
        return ctx.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     * @param ctx
     * @return
     */
    public static int getStatusBarHeight(Context ctx) {
        return getIdentifierValue(ctx, "status_bar_height", 25);
    }

    /**
     * 获取除状态栏高度
     * @param ctx
     * @return
     */
    public static int getHeightExcludeStatusBar(Context ctx){
        return getScreenHeight(ctx) - getStatusBarHeight(ctx);
    }

    /**
     * 获取导航栏高度
     * @param ctx
     * @return
     */
    public static int getNavigationHeight(Context ctx) {
        int rid = ctx.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return rid == 0 ? 0 : getIdentifierValue(ctx, "navigation_bar_height", 50);
    }

    private static int getIdentifierValue(Context ctx, String name, int defDpValue){
        int height;
        Resources res = ctx.getResources();
        int resourceId = res.getIdentifier(name,"dimen","android");
        if (resourceId > 0) {
            height = res.getDimensionPixelSize(resourceId);
        }else{
            height = (int) res.getDisplayMetrics().density * defDpValue;
        }
        return height;
    }

    /**
     * 强制隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus() == null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取安卓设备的唯一id
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        if (context == null) {
            return "";
        }
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            return "";
        }
        return androidId;
    }

}