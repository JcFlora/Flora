package com.jc.flora.apps.component.video.delegate;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
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
import com.jc.flora.apps.component.video.player.BasePlayer;
import com.jc.flora.apps.component.video.player.OnPreparedListener;
import com.jc.flora.apps.component.video.player.PlayerFactory;

import java.util.ArrayList;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/4/3.
 */

public class VideoDelegate24 extends Fragment {

    private static final String TAG = "VideoDelegate24";

    // mp4列表
    private ArrayList<MP4> mMp4List;
    // 视频视图
    private TextureView mTextureView;
    // 播放器
    private BasePlayer mPlayer;
    // 缓冲区
    private Surface mSurface;
    // 视频缓冲数据
    private SurfaceTexture mSurfaceTexture;

    // 当前播放的mp4文件索引
    private int mCurrentMp4Index = -1;
    // 当前播放位置
    private int mCurrentPosition = -1;
    // 是否创建新的缓冲区，无缝播放设置false，切换新视频时设置true
    private boolean mNeedNewSurface = false;
    /** ActivityOnPause时当前视频正在播放 */
    private boolean mIsVideoPlayingWhenActivityOnPause = false;
    /** 当前界面正处在前台运行 */
    private boolean mIsInForeground = true;
    /** 准备好之后是否自动播放 */
    private boolean mAutoStart = false;

    public void setPlayerType(PlayerFactory.PlayerType type) {
        mPlayer = PlayerFactory.get(type);
    }

    public void setTextureView(TextureView textureView) {
        mTextureView = textureView;
    }

    /**
     * 添加视频播放状态监听回调
     * @param l 视频播放状态监听回调
     */
    public void addVideoStatusListener(VideoStatusListener l) {
        mPlayer.addVideoStatusListener(l);
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
        if (mPlayer.available() && !mPlayer.isPlaying()) {
            // 添加拦截
            if(mPlayer.callbackPlayIntercepted()){
                return;
            }
            mPlayer.start();
            // 添加播放的回调
            mPlayer.callbackWhenPlay();
        }
    }

    /**
     * 暂停播放
     */
    public void pauseVideo() {
        if (mPlayer.available() && mPlayer.isPlaying()) {
            mPlayer.pause();
            // 添加暂停播放的回调
            mPlayer.callbackWhenPause();
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
                // 添加拦截
                if(mPlayer.callbackPlayIntercepted()){
                    return;
                }
                mAutoStart = true;
                release();
                recreate();
            }
        });
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {
        if (mPlayer.available()) {
            mPlayer.release();
        }
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

    public void seekTo(int progress) {
        mPlayer.seekTo(progress);
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
        if (!mIsInForeground && mPlayer.available()) {
            mIsInForeground = true;
            if(mCurrentPosition >= 0){
                mPlayer.seekTo(mCurrentPosition);
            }
            if (mIsVideoPlayingWhenActivityOnPause) {
                playVideo();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer.available()) {
            mIsVideoPlayingWhenActivityOnPause = mPlayer.isPlaying();
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
                    mAutoStart = false;
                    recreate();
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

    private void recreate() {
        if(mPlayer.available() || mSurface == null || mCurrentMp4Index < 0){
            return;
        }
        mPlayer.init(getContext());
        mPlayer.setSurface(mSurface);
        Uri uri = mMp4List.get(mCurrentMp4Index).getVideoUri(getContext());
        mPlayer.setOnPreparedListener(mOnPreparedListener);
        mPlayer.setDataSource(getContext(), uri);
        if(mAutoStart && mIsInForeground){
            // 添加准备开始的回调
            mPlayer.callbackWhenPrepareStart(mCurrentMp4Index);
        }
    }

    private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(BasePlayer player) {
            mPlayer.callbackWhenSelect(mCurrentMp4Index);
            if (mAutoStart && mIsInForeground) {
                playVideo();
                // 添加准备结束的回调
                mPlayer.callbackWhenPrepareEnd();
            }
            initProgress();
        }
    };

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
            int position = mPlayer.getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if(position != mCurrentPosition){
                mCurrentPosition = position;
                // 播放位置的回调
                mPlayer.callbackWhenProgress(position);
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

}