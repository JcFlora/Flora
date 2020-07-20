package com.jc.flora.apps.scene.splash.delegate;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * 闪屏业务管理：封装优化+屏蔽back键+退出关闭倒计时任务+倒计时显示+skip
 * 每次从Home切回来的时候，只需要等待剩余时长即可
 * Created by shijincheng on 2017/1/24.
 */
public class Splash5Delegate extends Fragment {

    /** 倒计时时长 */
    private static final long SHOW_TIME_MIN = 15000;
    /** 数秒周期 */
    private static final long TICK_PERIODS = 100;

    private Class<? extends AppCompatActivity> mTargetActivity;
    private TextView mTickView;
    private long mDelayedTime = SHOW_TIME_MIN;
    private SplashCountDownTimer mCountDownTimer;

    public Splash5Delegate setTargetActivity(Class<? extends AppCompatActivity> targetActivity) {
        mTargetActivity = targetActivity;
        return this;
    }

    public Splash5Delegate setTickView(TextView tickView){
        mTickView = tickView;
        return this;
    }

    public Splash5Delegate setSkipButton(View skipButton){
        if(skipButton == null){
            return this;
        }
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
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
        mCountDownTimer = new SplashCountDownTimer(mDelayedTime);
        mCountDownTimer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private class SplashCountDownTimer extends CountDownTimer{

        public SplashCountDownTimer(long millisInFuture) {
            super(millisInFuture, TICK_PERIODS);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mDelayedTime = millisUntilFinished;
            long seconds = (millisUntilFinished+750) / 1000;
            if (mTickView != null && seconds != 0) {
                mTickView.setText(seconds + "秒");
            }
        }

        @Override
        public void onFinish() {
            gotoMain();
        }
    }

    private void gotoMain(){
        Activity activity = getActivity();
        if (mTargetActivity == null || activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        startActivity(new Intent(activity, mTargetActivity));
        activity.finish();
    }

}
