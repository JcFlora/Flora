package com.jc.flora.apps.component.video.player;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;

import com.jc.flora.apps.component.video.delegate.VideoStatusListener;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public abstract class BasePlayer {

    // 准备状态的监听器
    protected OnPreparedListener mOnPreparedListener;

    // 视频播放状态监听器集合
    private ArrayList<VideoStatusListener> mVideoStatusListeners = new ArrayList<>();

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        mOnPreparedListener = onPreparedListener;
    }

    /**
     * 添加视频播放状态监听回调
     * @param l 视频播放状态监听回调
     */
    public void addVideoStatusListener(VideoStatusListener l) {
        mVideoStatusListeners.add(l);
    }

    public abstract void init(Context context);

    public abstract boolean available();

    public abstract boolean isPlaying();

    public abstract int getCurrentPosition();

    public abstract int getDuration();

    public abstract void start();

    public abstract void pause();

    public abstract void seekTo(int msc);

//    public abstract void stop();

    public abstract void release();

    public abstract void setSurface(Surface surface);

    public abstract void setDataSource(Context context, Uri uri);

    public boolean callbackPlayIntercepted(){
        // 添加拦截
        for (VideoStatusListener l : mVideoStatusListeners) {
            if(l.onPlayIntercepted()){
                return true;
            }
        }
        return false;
    }

    public void callbackWhenSelectStart(int index){
        // 添加选择或切换开始时的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onSelectStart(index);
        }
    }

    public void callbackWhenPrepareStart(int index){
        // 添加准备开始的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPrepareStart(index);
        }
    }

    public void callbackWhenSelectEnd(int index){
        // 添加选择或切换结束的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onSelectEnd(index, getDuration());
        }
    }

    public void callbackWhenPrepareEnd(){
        // 添加准备结束的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPrepareEnd();
        }
    }

    public void callbackWhenPlay(){
        // 添加播放的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPlay();
        }
    }

    public void callbackWhenPause(){
        // 添加暂停播放的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onPause();
        }
    }

    public void callbackWhenStop(){
        // 添加停止播放的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onStop();
        }
    }

    public void callbackWhenProgress(int position){
        // 播放位置的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onProgress(position);
        }
    }

    protected void callbackWhenComplete(){
        // 添加播放完成的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onComplete();
        }
    }

    protected void callbackWhenError(){
        // 添加播放出错的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onError();
        }
    }

    protected void callbackWhenBufferingStart(){
        // 添加缓冲开始的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onBufferingStart();
        }
    }

    protected void callbackWhenBufferingEnd(){
        // 添加缓冲结束的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onBufferingEnd();
        }
    }

    protected void callbackWhenBufferingUpdate(int percent){
        // 添加缓冲进度变化的回调
        for (VideoStatusListener l : mVideoStatusListeners) {
            l.onBufferingUpdate(percent);
        }
    }

}