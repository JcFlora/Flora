package com.jc.flora.apps.component.video.delegate;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.jc.flora.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/24.
 */

public class VideoDelegate7 extends Fragment {

    // 视频视图
    private VideoView mVideoView;

    private int mVideoPosition = 0;
    /** 当前视频正在播放 */
    private boolean mIsVideoPlaying = false;
    /** 当前界面正处在前台运行 */
    private boolean mIsInForeground = true;

    // 视频播放状态监听器集合
    private ArrayList<VideoStatusListener> mVideoStatusListeners = new ArrayList<>();

    public void setVideoView(VideoView videoView) {
        mVideoView = videoView;
    }

    /**
     * 添加视频播放状态监听回调
     * @param l 视频播放状态监听回调
     */
    public void addVideoStatusListener(VideoStatusListener l) {
        mVideoStatusListeners.add(l);
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void start() {
        mVideoView.start();
        // 添加播放的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPlay();
        }
    }

    public void pause() {
        mVideoView.pause();
        // 添加暂停播放的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPause();
        }
    }

    public void seekTo(int progress) {
        mVideoView.seekTo(progress);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsInForeground && mVideoView != null) {
            mIsInForeground = true;
            mVideoView.seekTo(mVideoPosition);
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
            mIsVideoPlaying = mVideoView.isPlaying();
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
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void init() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setMaxProgress();
                mp.seekTo(480);
                setRemoveBgWhenFirstPlayListener(mp);
                if(mIsInForeground && mIsVideoPlaying){
                    start();
                }
                initProgress();
            }
        });
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.rainbow);
    }

    private void setMaxProgress(){
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onSelect(0, mVideoView.getDuration());
        }
    }

    private void initProgress() {
        // 开始不停地刷新播放进度
        mProgressRefreshHandler.sendEmptyMessage(0);
    }

    // 用于刷新播放进度的Handler
    @SuppressLint("HandlerLeak")
    private Handler mProgressRefreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onProgress(mVideoView.getCurrentPosition());
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

}