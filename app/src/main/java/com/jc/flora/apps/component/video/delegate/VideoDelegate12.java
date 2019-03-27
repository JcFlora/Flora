package com.jc.flora.apps.component.video.delegate;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;

import com.jc.flora.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/24.
 */

public class VideoDelegate12 extends Fragment {

    // 视频视图
    private TextureView mTextureView;
    // 播放器
    private MediaPlayer mMediaPlayer;
    private Surface mSurface;

    private int mVideoPosition = 0;
    /** 当前视频正在播放 */
    private boolean mIsVideoPlaying = false;
    /** 当前界面正处在前台运行 */
    private boolean mIsInForeground = true;

    // 视频播放状态监听器集合
    private ArrayList<VideoStatusListener> mVideoStatusListeners = new ArrayList<>();

    public void setTextureView(TextureView textureView) {
        mTextureView = textureView;
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

    /**
     * 播放视频（继续播放）
     */
    public void playVideo() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            // 添加播放的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onPlay();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pauseVideo() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            // 添加暂停播放的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onPause();
            }
        }
    }

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsInForeground && mMediaPlayer != null) {
            mIsInForeground = true;
            mMediaPlayer.seekTo(mVideoPosition);
            if (mIsVideoPlaying) {
                playVideo();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mVideoPosition = mMediaPlayer.getCurrentPosition();
            mIsVideoPlaying = mMediaPlayer.isPlaying();
            pauseVideo();
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
        release();
    }

    private void init(){
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mSurface = new Surface(surface);
            recreate();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }

    };

    private void recreate() {
        if(mMediaPlayer != null || mSurface == null){
            return;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setSurface(mSurface);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setMaxProgress();
                mp.seekTo(480);
                if(mIsInForeground && mIsVideoPlaying){
                    playVideo();
                }
                initProgress();
            }
        });
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.rainbow;
        try {
            mMediaPlayer.setDataSource(mTextureView.getContext(), Uri.parse(videoPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    private void setMaxProgress(){
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onSelect(0, mMediaPlayer.getDuration());
        }
    }

    private void initProgress() {
//        // 初始化播放位置
//        mCurrentPosition = -1;
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
                l.onProgress(mMediaPlayer.getCurrentPosition());
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

}