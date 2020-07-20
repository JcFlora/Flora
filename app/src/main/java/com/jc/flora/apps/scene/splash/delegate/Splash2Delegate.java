package com.jc.flora.apps.scene.splash.delegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 闪屏业务管理：封装优化+屏蔽back键+退出关闭倒计时任务
 * 每次从Home切回来的时候，都要经历完整的SHOW_TIME_MIN的时长
 * Created by shijincheng on 2017/1/23.
 */
public class Splash2Delegate extends Fragment {

    private static final long SHOW_TIME_MIN = 3000;
    private Class<? extends AppCompatActivity> mTargetActivity;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public Splash2Delegate setTargetActivity(Class<? extends AppCompatActivity> targetActivity) {
        mTargetActivity = targetActivity;
        return this;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(mGotoMainTask, SHOW_TIME_MIN);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    private Runnable mGotoMainTask = new Runnable() {
        @Override
        public void run() {
            gotoMain();
        }
    };

    private void gotoMain(){
        Activity activity = getActivity();
        if (mTargetActivity == null || activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        startActivity(new Intent(activity, mTargetActivity));
        activity.finish();
    }

}
