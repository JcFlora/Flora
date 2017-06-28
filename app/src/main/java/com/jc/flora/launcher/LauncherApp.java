package com.jc.flora.launcher;

import android.app.Activity;

/**
 * Created by shijincheng on 2017/1/12.
 */
public class LauncherApp {
    /** app名称 */
    public String appName;
    /** app图标 */
    public int appIconResId;
    /** app跳转界面 */
    public Class<? extends Activity> targetActivity;

    public LauncherApp(){
    }

    public LauncherApp(String appName, int appIconResId, Class<? extends Activity> targetActivity) {
        this.appName = appName;
        this.appIconResId = appIconResId;
        this.targetActivity = targetActivity;
    }
}
