package com.jc.flora.apps.component.video.delegate;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.time.TimeUtils;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;

import java.util.HashMap;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2018/8/28.
 */

public class VideoDelegate6 extends Fragment {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    // 标题
    private View mVHead;
    // 视频父布局
    private View mLayoutVideo;
    // 视频视图
    private VideoView mVideoView;
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

    // 当前播放位置
    private int mCurrentPosition = -1;
    // ActivityOnPause时的播放位置
    private int mVideoPositionWhenActivityOnPause = 0;
    /** ActivityOnPause时当前视频正在播放 */
    private boolean mIsVideoPlayingWhenActivityOnPause = false;
    /** 当前界面正处在前台运行 */
    private boolean mIsInForeground = true;

    public void setHead(View vHead) {
        mVHead = vHead;
    }

    public void setLayoutVideo(View layoutVideo) {
        mLayoutVideo = layoutVideo;
    }

    public void setVideoView(VideoView videoView) {
        mVideoView = videoView;
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
        if (!mIsInForeground && mVideoView != null) {
            mIsInForeground = true;
            mVideoView.seekTo(mVideoPositionWhenActivityOnPause);
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPositionWhenActivityOnPause = mVideoView.getCurrentPosition();
            mIsVideoPlayingWhenActivityOnPause = mVideoView.isPlaying();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mIsInForeground = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLayoutVideo.removeCallbacks(mFadeOut);
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void initView() {
        Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
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
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mBtnPlay.setImageResource(R.drawable.video_play);
                } else {
                    mVideoView.start();
                    mBtnPlay.setImageResource(R.drawable.video_pause);
                }
                showController();
            }
        });
        mBtnSwitchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screenOrientation = getActivity().getRequestedOrientation();
                boolean isLandscape = screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
                getActivity().setRequestedOrientation(isLandscape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                showController();
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setMaxProgress();
                setRemoveBgWhenFirstPlayListener(mp);
                if(mIsInForeground && mIsVideoPlayingWhenActivityOnPause){
                    mp.start();
                }
            }
        });
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.rainbow);
        initProgress();
    }

    private void initProgress() {
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿卡顿
                if(fromUser){
                    mVideoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 手指开始滑动时，SeekBar近似常显
                showController(3600000);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 手指停止滑动时，SeekBar短暂显示
                showController();
            }
        });
        // 开始不停地刷新播放进度
        mProgressRefreshHandler.sendEmptyMessage(0);
    }

    private void hideController(){
        mLayoutController.setVisibility(View.GONE);
    }

    private void showController(){
        showController(3000);
    }

    private void showController(int timeout){
        mLayoutController.setVisibility(View.VISIBLE);
        mLayoutVideo.removeCallbacks(mFadeOut);
        mLayoutVideo.postDelayed(mFadeOut, timeout);
    }

    private void setMaxProgress(){
        // 初始化播放最大进度值
        mSbProgress.setMax(mVideoView.getDuration());
        // 初始化总时间
        mTvMaxTime.setText(TimeUtils.getTimeSmartFormat(mVideoView.getDuration()));

    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hideController();
        }
    };

    // 用于刷新播放进度的Handler
    @SuppressLint("HandlerLeak")
    private Handler mProgressRefreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取最新的播放位置
            int position = mVideoView.getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if(position != mCurrentPosition){
                mCurrentPosition = position;
                mSbProgress.setProgress(position);
                String currentTime = TimeUtils.getTimeSmartFormat(position);
                mTvCurrentTime.setText(currentTime);
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

    /**
     * 设置第一帧画面为背景，仅适用于网络视频，不适用于本地
     * @param uri 网络视频地址
     */
    private void setFirstFrameForBackground(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri, new HashMap<String, String>());
        Bitmap bitmap = retriever.getFrameAtTime();
        mVideoView.setBackground(new BitmapDrawable(bitmap));
        retriever.release();
    }

    /**
     * 第一次播放时，移除VideoView的封面图
     * @param mp
     */
    private void setRemoveBgWhenFirstPlayListener(MediaPlayer mp){
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    mVideoView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mVideoView.setBackground(null);
                        }
                    },400);
                }
                return true;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            setFullScreen();
            Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mBtnSwitchScreen.setImageResource(R.drawable.video_normal_screen);
            mVHead.setVisibility(View.GONE);
        }else{
            quitFullScreen();
            Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                    ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
            mBtnSwitchScreen.setImageResource(R.drawable.video_full_screen);
            mVHead.setVisibility(View.VISIBLE);
        }
    }

    private void setFullScreen(){
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void quitFullScreen(){
        final WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().setAttributes(attrs);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public boolean onBackPressed(){
        int screenOrientation = getActivity().getRequestedOrientation();
        boolean isLandscape = screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        if(isLandscape){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return isLandscape;
    }

}