package com.jc.flora.apps.component.video.delegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.time.TimeUtils;
import com.jc.flora.apps.component.video.widget.BaseVideoGestureCover;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/31.
 */

public class VideoControllerDelegate20 extends Fragment {

    // 视频父布局
    private View mLayoutVideo;
    // 控制层布局
    private View mLayoutController;
    // 播放按钮
    private ImageView mBtnPlay;
    // 当前播放进度时间显示
    private TextView mTvCurrentTime;
    // 播放进度条
    private SeekBar mSbProgress;
    // 总进度时间显示
    private TextView mTvMaxTime;
    // 全屏/小屏切换
    private ImageView mBtnSwitchScreen;
    // 手势控制浮层
    private BaseVideoGestureCover mGestureCover;
    // 状态标记，标识是否正在播放，用来控制播放按钮
    private boolean mIsPlaying;

    private BaseVideoDelegate mVideoDelegate;

    public void setLayoutVideo(View layoutVideo) {
        mLayoutVideo = layoutVideo;
    }

    public void setLayoutController(View layoutController) {
        mLayoutController = layoutController;
    }

    public void setBtnPlay(ImageView btnPlay) {
        mBtnPlay = btnPlay;
    }

    public void setTvCurrentTime(TextView tvCurrentTime) {
        mTvCurrentTime = tvCurrentTime;
    }

    public void setSbProgress(SeekBar sbProgress) {
        mSbProgress = sbProgress;
    }

    public void setTvMaxTime(TextView tvMaxTime) {
        mTvMaxTime = tvMaxTime;
    }

    public void setBtnSwitchScreen(ImageView btnSwitchScreen) {
        mBtnSwitchScreen = btnSwitchScreen;
    }

    public void setGestureCover(BaseVideoGestureCover gestureCover) {
        mGestureCover = gestureCover;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initProgress();
        initDelegate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLayoutVideo.removeCallbacks(mFadeOut);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initController() {
        mLayoutController.setVisibility(View.GONE);
        mLayoutVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mLayoutController.getVisibility() == View.VISIBLE) {
                        hideController();
                    }else{
                        showController();
                    }
                }
                return false;
            }
        });
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying) {
                    mVideoDelegate.pauseVideo();
                } else {
                    mVideoDelegate.playVideo();
                }
                showController();
            }
        });
        mBtnSwitchScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showController();
                }
                return false;
            }
        });
        mGestureCover.setOnSingleTapUpListener(new BaseVideoGestureCover.OnSingleTapUpListener() {
            @Override
            public void onSingleTapUp() {
                if (mLayoutController.getVisibility() == View.VISIBLE) {
                    hideController();
                }else{
                    showController();
                }
            }
        });
    }

    private void initProgress() {
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿卡顿
                if(fromUser){
                    mVideoDelegate.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 手指开始滑动时，SeekBar近似常显
                showController(3600_000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 手指停止滑动时，SeekBar短暂显示
                showController();
            }
        });
    }

    private void initDelegate() {
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener() {

            @Override
            public void onPrepareStart(int index) {
                // 初始化当前进度
                mSbProgress.setProgress(0);
                // 初始化当前时间
                mTvCurrentTime.setText("00:00");
            }

            @Override
            public void onSelectEnd(int index, int maxProgress) {
                // 初始化播放最大进度值
                mSbProgress.setMax(maxProgress);
                // 初始化总时间
                mTvMaxTime.setText(TimeUtils.getTimeSmartFormat(maxProgress));
            }

            @Override
            public void onPlay() {
                mIsPlaying = true;
                mBtnPlay.setImageResource(R.drawable.video_pause);
            }

            @Override
            public void onBufferingUpdate(int percent) {
                int secondaryProgress = (int) (mSbProgress.getMax() * percent / 100f + 0.5f);
                mSbProgress.setSecondaryProgress(secondaryProgress);
            }

            @Override
            public void onPause() {
                mIsPlaying = false;
                mBtnPlay.setImageResource(R.drawable.video_play);
            }

            @Override
            public void onStop() {
                mIsPlaying = false;
                mBtnPlay.setImageResource(R.drawable.video_play);
            }

            @Override
            public void onComplete() {
                mIsPlaying = false;
                mBtnPlay.setImageResource(R.drawable.video_play);
            }

            @Override
            public void onProgress(int progress) {
                mSbProgress.setProgress(progress);
                mTvCurrentTime.setText(TimeUtils.getTimeSmartFormat(progress));
            }

        });
    }

    private void hideController(){
        mLayoutController.setVisibility(View.GONE);
    }

    public void showController(){
        showController(3000);
    }

    private void showController(int timeout){
        mLayoutController.setVisibility(View.VISIBLE);
        mLayoutVideo.removeCallbacks(mFadeOut);
        mLayoutVideo.postDelayed(mFadeOut, timeout);
    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hideController();
        }
    };

}