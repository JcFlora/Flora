package com.jc.flora.apps.component.deviceinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

/**
 * Created by shijincheng on 2017/3/20.
 */
public class DeviceUtil {

    /**
     * 当前系统是否是4.4W 以上
     * @return
     */
    public static boolean isSystemVersionAfterKitkatWatch(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * 当前系统是否是5.0以上
     * @return
     */
    public static boolean isSystemVersionAfterLollipop(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 当前系统是否是6.0以上
     * @return
     */
    public static boolean isSystemVersionAfterM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
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
        return getIdentifierHeight(ctx, "status_bar_height", 25);
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
        return rid == 0 ? 0 : getIdentifierHeight(ctx, "navigation_bar_height", 50);
    }

    private static int getIdentifierHeight(Context ctx, String name, int defDpValue){
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

}
