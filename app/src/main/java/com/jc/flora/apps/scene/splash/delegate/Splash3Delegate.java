package com.jc.flora.apps.scene.splash.delegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * 闪屏业务管理：封装优化+屏蔽back键+退出关闭倒计时任务+预加载+跳转到不同界面
 * 每次从Home切回来的时候，只需要等待剩余时长即可
 * Created by shijincheng on 2017/1/23.
 */
public class Splash3Delegate extends Fragment {

    private static final long SHOW_TIME_MIN = 3000;
    private Class<? extends AppCompatActivity> mTargetActivity;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private long mDelayedTime = SHOW_TIME_MIN;
    private boolean mCouldStart = false;
    private boolean mIsForeground = false;

    public Splash3Delegate setTargetActivity(Class<? extends AppCompatActivity> targetActivity) {
        mTargetActivity = targetActivity;
        return this;
    }

    public Splash3Delegate addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
        return this;
    }

    /**
     * mCouldStart和mIsForeground两个标记，保证此方法和onStart()互锁，使mHandler只会发送一次消息
     * @param taskTime
     */
    public void startForwardWhenTaskCompleted(long taskTime){
        long time = SHOW_TIME_MIN - taskTime ;
        mDelayedTime = time > 0 ? time : 0;
        mCouldStart = true;
        if (mIsForeground){
            mHandler.postDelayed(mGotoMainTask, mDelayedTime);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsForeground = true;
        if (mCouldStart) {
            mHandler.postDelayed(mGotoMainTask, mDelayedTime);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mIsForeground = false;
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
