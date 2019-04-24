package com.jc.flora.apps.component.audio.delegate;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Samurai on 2017/10/15.
 */
public class AudioDelegate12 extends Binder {

    // mp3列表
    private ArrayList<MP3> mMp3List;
    // 上下文
    private Context mContext;
    // 播放音频的核心组件MediaPlayer
    private MediaPlayer mMediaPlayer;
    // 当前播放的mp3文件索引
    private int mCurrentMp3Index = 0;
    // 当前播放位置
    private int mCurrentPosition = -1;
    // 音频播放状态监听器
    private AudioStatusListener mAudioStatusListener;

    // 播放模式
    private AudioPlayMode mPlayMode = AudioPlayMode.LOOP;

    public AudioDelegate12(Context ctx) {
        mContext = ctx;
    }

    /**
     * 设置播放的mp3列表
     * @param mp3List 播放的mp3列表
     */
    public void setMp3List(ArrayList<MP3> mp3List) {
        mMp3List = mp3List;
        mCurrentMp3Index = 0;
        recreate();
    }

    /**
     * 设置播放模式
     * @param audioPlayMode 播放模式
     */
    public void setPlayMode(AudioPlayMode audioPlayMode) {
        mPlayMode = audioPlayMode;
        // 切换播放模式时回调
        if(mAudioStatusListener != null){
            mAudioStatusListener.onModeSelect(audioPlayMode.value());
        }
    }

    /**
     * 设置音频播放状态监听回调
     * @param l 音频播放状态监听回调
     */
    public void setAudioStatusListener(AudioStatusListener l) {
        mAudioStatusListener = l;
    }

    /**
     * 播放音频（继续播放）
     */
    public void playAudio(){
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            if(mAudioStatusListener != null){
                mAudioStatusListener.onPlay();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pauseAudio(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if(mAudioStatusListener != null){
                mAudioStatusListener.onPause();
            }
        }
    }

    /**
     * 复位（从头开始播放）
     */
    public void resetAudio() {
        release();
        recreate();
        start();
    }

    /**
     * 切换音频（从头开始播放）
     * 修改为默认列表循环播放模式
     * @param index 切换的音频索引
     */
    public void selectAudio(int index) {
        if(index < 0){
            // 第一首的上一首切换成最后一首
            mCurrentMp3Index = mMp3List.size() - 1;
        }else if(index >= mMp3List.size()){
            // 最后一首的下一首切换成第一首
            mCurrentMp3Index = 0;
        }else{
            mCurrentMp3Index = index;
        }
        resetAudio();
    }

    /**
     * 播放下一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void nextAudio() {
        // 原来的逻辑默认为列表循环模式，新添加单曲模式和随机模式
        switch (mPlayMode) {
            case SINGLE:
                selectAudio(mCurrentMp3Index);
                break;
            case SHUFFLE:
                selectAudio(new Random().nextInt(mMp3List.size()));
                break;
            case LOOP:
            default:
                selectAudio(mCurrentMp3Index + 1);
                break;
        }
    }

    /**
     * 播放上一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void preAudio() {
        // 原来的逻辑默认为列表循环模式，新添加单曲模式和随机模式
        switch (mPlayMode) {
            case SINGLE:
                selectAudio(mCurrentMp3Index);
                break;
            case SHUFFLE:
                selectAudio(new Random().nextInt(mMp3List.size()));
                break;
            case LOOP:
            default:
                selectAudio(mCurrentMp3Index - 1);
                break;
        }
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
            if(mAudioStatusListener != null){
                mAudioStatusListener.onStop();
            }
            mProgressRefreshHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate() {
        if (mMediaPlayer == null) {
            // 播放声音
            mMediaPlayer = MediaPlayer.create(mContext, mMp3List.get(mCurrentMp3Index).resId);
            if (mMediaPlayer == null) {
                return;
            }
            // 不循环
            mMediaPlayer.setLooping(false);
            // 添加播放完成自动播放下一首的监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextAudio();
                }
            });
            // 选择或切换的回调
            if (mAudioStatusListener != null) {
                mAudioStatusListener.onSelect(mCurrentMp3Index, mMediaPlayer.getDuration());
            }
            // 初始化播放位置
            mCurrentPosition = -1;
            // 开始不停地刷新播放进度
            mProgressRefreshHandler.sendEmptyMessage(0);
        }
    }

    // 用于刷新播放进度的Handler
    private Handler mProgressRefreshHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取最新的播放位置
            int position = getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if(position != mCurrentPosition){
                mCurrentPosition = position;
                // 播放位置的回调
                if(mAudioStatusListener != null){
                    mAudioStatusListener.onProgress(position);
                }
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    /**
     * 播放音频
     */
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            if (mAudioStatusListener != null) {
                mAudioStatusListener.onPlay();
            }
        }
    }

    /**
     * 获取当前播放的mp3文件索引
     * @return 当前播放的mp3文件索引
     */
    public int getCurrentMp3Index() {
        return mCurrentMp3Index;
    }

    /**
     * 获取歌曲长度
     **/
    public int getMaxProgress() {
        return mMediaPlayer.getDuration();
    }

    /**
     * 获取播放位置
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 播放指定位置
     */
    public void seekToPosition(int msec) {
        mMediaPlayer.seekTo(msec);
    }

}