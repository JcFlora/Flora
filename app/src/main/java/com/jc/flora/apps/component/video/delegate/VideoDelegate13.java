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
import android.view.View;

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
    // 视频视图
    private TextureView mTextureView;
    // 播放器
    private MediaPlayer mMediaPlayer;
    // 缓冲区
    private Surface mSurface;
    // 视频缓冲数据
    private SurfaceTexture mSurfaceTexture;

    // 当前播放的mp4文件索引
    private int mCurrentMp4Index = -1;
    // 当前播放位置
    private int mCurrentPosition = -1;
    // 是否需要创建新的缓冲区，无缝播放设置false，切换新视频时设置true
    private boolean mNeedNewSurface = false;
    /** ActivityOnPause时当前视频正在播放 */
    private boolean mIsVideoPlayingWhenActivityOnPause = false;
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
        boolean isFirstSetData = (mMp4List == null);
        mMp4List = mp4List;
        mCurrentMp4Index = -1;
        if(!isFirstSetData){
            release();
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

    /**
     * 切换视频（从头开始播放）
     * @param index 切换的视频索引
     */
    public void selectVideo(int index) {
        selectVideo(index, false);
    }

    /**
     * 切换视频（从头开始播放）
     * @param index 切换的视频索引
     * @param needNewSurface 是否需要新的缓冲区
     */
    public void selectVideo(int index, boolean needNewSurface) {
        if(index < 0){
            // 第一个的上一个切换成最后一个
            mCurrentMp4Index = mMp4List.size() - 1;
        }else if(index >= mMp4List.size()){
            // 最后一个的下一个切换成第一个
            mCurrentMp4Index = 0;
        }else{
            mCurrentMp4Index = index;
        }
        mNeedNewSurface = needNewSurface;
        resetVideo();
    }

    /**
     * 复位（从头开始播放）
     */
    public void resetVideo() {
        pauseVideo();
        mProgressRefreshHandler.post(new Runnable() {
            @Override
            public void run() {
                release();
                recreate(true);
            }
        });
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            // 添加停止播放的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onStop();
            }
        }
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    public int getCurrentMp4Index() {
        return mCurrentMp4Index;
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
            if(mCurrentPosition >= 0){
                mMediaPlayer.seekTo(mCurrentPosition);
            }
            if (mIsVideoPlayingWhenActivityOnPause) {
                playVideo();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mIsVideoPlayingWhenActivityOnPause = mMediaPlayer.isPlaying();
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
        mTextureView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if(mSurfaceTexture != null && !mNeedNewSurface){
                    mTextureView.setSurfaceTexture(mSurfaceTexture);
                }
            }
            @Override
            public void onViewDetachedFromWindow(View v) {
            }
        });
    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if(mSurfaceTexture == null || mNeedNewSurface){
                mSurfaceTexture = surface;
                mSurface = new Surface(surface);
                if(!mNeedNewSurface){
                    recreate(false);
                }
                mNeedNewSurface = false;
            }
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

    private void recreate(final boolean autoStart) {
        if(mMediaPlayer != null || mSurface == null || mCurrentMp4Index < 0){
            return;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setSurface(mSurface);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setMaxProgress();
                if (autoStart && mIsInForeground) {
                    playVideo();
                }
                initProgress();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 添加播放完成的回调
                for (VideoStatusListener l : mVideoStatusListeners) {
                    l.onComplete();
                }
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
        // 初始化播放位置
        mCurrentPosition = -1;
        // 开始不停地刷新播放进度
        mProgressRefreshHandler.sendEmptyMessage(0);
    }

    // 用于刷新播放进度的Handler
    @SuppressLint("HandlerLeak")
    private Handler mProgressRefreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取最新的播放位置
            int position = mMediaPlayer.getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if(position != mCurrentPosition){
                mCurrentPosition = position;
                // 播放位置的回调
                for (VideoStatusListener l : mVideoStatusListeners) {
                    l.onProgress(position);
                }
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

}