package com.jc.flora.apps.component.distribute.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jc.flora.apps.component.distribute.DistributeActivity;

public class AppForegroundDelegate {

    /**
     * 这个变量用来记录应用进入前台的Activity数量，以此用来判断应用是否在前台
     * 如果进入onStart和onStop的数量对等，则应用在后台；
     * 如果onStart比onStop多，则应用在前台。
     */
    private static int sForeGroundActivityCount = 0;
    /**
     * 应用是否在前台，默认为true
     * 后续进入onStart状态的Activity数量大于一个为前台
     */
    private static boolean sIsForeGround = true;

    public static void init(Application application) {
        // 注册全局Activity生命周期监听器
        application.registerActivityLifecycleCallbacks(sActivityLifecycleCallbacks);
    }

    public static void destroy(Application application) {
        // 反注册全局Activity生命周期监听器
        application.unregisterActivityLifecycleCallbacks(sActivityLifecycleCallbacks);
    }

    /**
     * 当前应用是否在前台
     * @return
     */
    public static boolean isForeGround() {
        return sIsForeGround;
    }

    /**
     * 全局Activity生命周期监听器
     */
    private static Application.ActivityLifecycleCallbacks sActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            sForeGroundActivityCount++;
            if (sForeGroundActivityCount > 1) {
                sIsForeGround = true;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            // 在这里为了方便演示，屏蔽之前的Activity的影响
            // 实际项目中，在Application中启动和注销此功能，不需要这段if代码
            if(activity instanceof DistributeActivity){
                return;
            }
            sForeGroundActivityCount--;
            if (sForeGroundActivityCount == 0) {
                sIsForeGround = false;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

}
