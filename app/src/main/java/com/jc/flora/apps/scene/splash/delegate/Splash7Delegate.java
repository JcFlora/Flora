package com.jc.flora.apps.scene.splash.delegate;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;

/**
 * 闪屏业务管理：视频广告+倒计时+skip
 * 每次从Home切回来的时候，只需要等待剩余时长即可
 * Created by shijincheng on 2017/1/24.
 */
public class Splash7Delegate extends Fragment {

    /** 数秒周期 */
    private static final long TICK_PERIODS = 100;

    private Class<? extends AppCompatActivity> mTargetActivity;

    private VideoView mVideoView;
    private TextView mTickView;
    private long mDelayedTime = -1;
    private SplashCountDownTimer mCountDownTimer;
    private int mVideoPosition = 0;
    private boolean mIsStop = false;

    public Splash7Delegate setTargetActivity(Class<? extends AppCompatActivity> targetActivity) {
        mTargetActivity = targetActivity;
        return this;
    }

    public Splash7Delegate setVideoView(VideoView videoView) {
        mVideoView = videoView;
        return this;
    }

    public Splash7Delegate setTickView(TextView tickView) {
        mTickView = tickView;
        return this;
    }

    public Splash7Delegate setSkipButton(View skipButton) {
        if (skipButton == null) {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsStop && mVideoView != null) {
            mVideoView.seekTo(mVideoPosition);
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mIsStop = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void initView() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                if (mDelayedTime < 0) {
                    mDelayedTime = mp.getDuration() - 200;
                }
                if(mCountDownTimer == null){
                    mCountDownTimer = new SplashCountDownTimer(mDelayedTime);
                    mCountDownTimer.start();
                }
            }
        });
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
    }

    private class SplashCountDownTimer extends CountDownTimer {

        public SplashCountDownTimer(long millisInFuture) {
            super(millisInFuture, TICK_PERIODS);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mDelayedTime = millisUntilFinished;
            long seconds = (millisUntilFinished + 750) / 1000;
            if (mTickView != null && seconds != 0) {
                mTickView.setText(seconds + "秒");
            }
        }

        @Override
        public void onFinish() {
            gotoMain();
        }
    }

    private void gotoMain() {
        Activity activity = getActivity();
        if (mTargetActivity == null || activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        startActivity(new Intent(activity, mTargetActivity));
        activity.finish();
    }

}