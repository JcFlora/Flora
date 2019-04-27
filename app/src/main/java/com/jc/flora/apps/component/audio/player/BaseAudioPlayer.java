package com.jc.flora.apps.component.audio.player;

import android.content.Context;
import android.net.Uri;

import com.jc.flora.apps.component.audio.delegate.AudioPlaySpeed;
import com.jc.flora.apps.component.audio.delegate.AudioSourceInterceptor;
import com.jc.flora.apps.component.audio.delegate.AudioStatusListener;
import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2019/4/26.
 */

public abstract class BaseAudioPlayer {

    // 准备状态的监听器
    protected OnPreparedListener mOnPreparedListener;

    /** 播放状态连接桥 */
    protected StatusBridge mStatusBridge;

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        mOnPreparedListener = onPreparedListener;
    }

    public void setStatusBridge(StatusBridge statusBridge) {
        mStatusBridge = statusBridge;
    }

    // 音频播放状态监听器集合
    private ArrayList<AudioStatusListener> mAudioStatusListeners = new ArrayList<>();
    // 音频数据源拦截器集合
    private ArrayList<AudioSourceInterceptor> mAudioSourceInterceptors = new ArrayList<>();

    /**
     * 添加音频播放状态监听回调
     *
     * @param l 音频播放状态监听回调
     */
    public void addAudioStatusListener(AudioStatusListener l) {
        mAudioStatusListeners.add(l);
    }

    /**
     * 移除音频播放状态监听回调
     *
     * @param l 音频播放状态监听回调
     */
    public void removeAudioStatusListener(AudioStatusListener l) {
        mAudioStatusListeners.remove(l);
    }

    /**
     * 添加音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void addAudioSourceInterceptor(AudioSourceInterceptor i) {
        mAudioSourceInterceptors.add(i);
    }

    /**
     * 移除音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void removeAudioSourceInterceptor(AudioSourceInterceptor i) {
        mAudioSourceInterceptors.remove(i);
    }

    public abstract void init(Context context);

    public abstract boolean available();

    public abstract boolean isPlaying();

    public abstract int getCurrentPosition();

    public abstract int getDuration();

    public abstract void start();

    public abstract void pause();

    public abstract void seekTo(int msc);

    public abstract void stop();

    public abstract void release();

    public abstract void setSpeed(AudioPlaySpeed audioPlaySpeed);

    public abstract void setDataSource(Context context, Uri uri);

    public void syncMp3List(int mp3Index, int maxProgress, int progress, int modeIndex, int speedIndex){
        for (AudioStatusListener l : mAudioStatusListeners) {
            // 同步当前切换
            l.onSelect(mp3Index, maxProgress);
            if(available()){
                // 同步播放状态
                if (isPlaying()) {
                    l.onPlay();
                } else {
                    l.onPause();
                }
            }
            // 同步播放位置
            l.onProgress(progress);
            // 同步播放模式
            l.onModeSelect(modeIndex);
            // 同步播放速度
            l.onSpeedSelect(speedIndex);
        }
    }

    public boolean interceptSelect(ArrayList<MP3> mp3List, int index) {
        // 添加切换拦截
        for (AudioSourceInterceptor i : mAudioSourceInterceptors) {
            if(i.interceptSelect(mp3List, index)){
                return true;
            }
        }
        return false;
    }

    public boolean interceptPlay() {
        // 添加拦截
        for (AudioSourceInterceptor i : mAudioSourceInterceptors) {
            if(i.interceptPlay()){
                return true;
            }
        }
        return false;
    }

    public void callbackWhenPrepareStart(int index) {
        // 添加准备开始的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onPrepareStart(index);
        }
    }

    public void callbackWhenSelect(int index){
        // 选择或切换的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onSelect(index, getDuration());
        }
    }

    public void callbackWhenPrepareEnd() {
        // 添加准备结束的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onPrepareEnd();
        }
    }

    public void callbackWhenPlay() {
        // 添加播放的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onPlay();
        }
    }

    public void callbackWhenPause() {
        // 添加暂停播放的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onPause();
        }
    }

    public void callbackWhenStop() {
        // 添加停止播放的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onStop();
        }
    }

    public void callbackWhenProgress(int position){
        // 播放位置的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onProgress(position);
        }
    }

    protected void callbackWhenError() {
        // 添加播放出错的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onError();
        }
    }

    protected void callbackWhenBufferingStart() {
        // 添加缓冲开始的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onBufferingStart();
        }
    }

    protected void callbackWhenBufferingEnd() {
        // 添加缓冲结束的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onBufferingEnd();
        }
    }

    protected void callbackWhenBufferingUpdate(int percent) {
        // 添加缓冲进度变化的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onBufferingUpdate(percent);
        }
    }

    /**
     * 通知被拦截后的回调
     * @param index
     * @param flag
     */
    public void callbackWhenIntercepted(ArrayList<MP3> mp3List,int index, int flag){
        // 调用被拦截后的回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onIntercepted(mp3List, index, flag);
        }
    }

    public void callbackWhenSpeedSelect(int index) {
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onSpeedSelect(index);
        }
    }

    public void callbackWhenModeSelect(int index) {
        // 切换播放模式时回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onModeSelect(index);
        }
    }
}
