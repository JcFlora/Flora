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

import com.jc.flora.apps.component.video.model.MP4;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/26.
 */

public class VideoDelegate13 extends Fragment {

    // mp4列表
    private ArrayList<MP4> mMp4List;
    // 当前播放的mp4文件索引
    private int mCurrentMp4Index = 0;

    // 视频视图
    private TextureView mTextureView;
    // 播放器
    private MediaPlayer mMediaPlayer;
    // 缓冲区
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
     * 设置播放的mp4列表
     * @param mp4List 播放的mp4列表
     */
    public void setMp4List(ArrayList<MP4> mp4List) {
        mMp4List = mp4List;
        mCurrentMp4Index = 0;
        release();
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

    /**
     * 切换视频（从头开始播放）
     * @param index 切换的视频索引
     */
    public void selectVideo(int index) {
        if(index < 0){
            // 第一个的上一个切换成最后一个
            mCurrentMp4Index = mMp4List.size() - 1;
        }else if(index >= mMp4List.size()){
            // 最后一个的下一个切换成第一个
            mCurrentMp4Index = 0;
        }else{
            mCurrentMp4Index = index;
        }
        resetVideo();
    }

    /**
     * 复位（从头开始播放）
     */
    public void resetVideo() {
        release();
        recreate(true);
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

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
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
            recreate(false);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            mSurface = new Surface(surface);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mSurface.release();
            mSurface = null;
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }

    };

    private void recreate(final boolean autoStart) {
        if(mSurface == null){
            return;
        }
        if(mMediaPlayer != null){
            mMediaPlayer.setSurface(mSurface);
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
                if (mIsInForeground && (autoStart || mIsVideoPlaying)) {
                    playVideo();
                }
                initProgress();
            }
        });
        Uri uri = mMp4List.get(mCurrentMp4Index).getVideoUri(getContext());
        try {
            mMediaPlayer.setDataSource(getContext(), uri, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    private void setMaxProgress(){
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onSelect(mCurrentMp4Index, mMediaPlayer.getDuration());
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