package com.jc.flora.apps.component.audio.delegate;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.audio.player.AudioPlayerFactory;
import com.jc.flora.apps.component.audio.player.BaseAudioPlayer;
import com.jc.flora.apps.component.audio.player.OnPreparedListener;
import com.jc.flora.apps.component.audio.player.StatusBridge;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Samurai on 2019/4/26.
 */
public class AudioDelegate31 extends BaseAudioDelegate {

    /** 快进快退时间间隔：15秒 */
    private static final int FORWARD_POSITION = 15_000;

    // mp3列表
    private ArrayList<MP3> mMp3List;
    // 上下文
    private Context mContext;
    // 播放器
    private BaseAudioPlayer mPlayer;

    // 当前播放的mp3文件索引
    private int mCurrentMp3Index = 0;
    // 当前播放位置
    private int mCurrentPosition = 1;
    // 进入Error状态时候的播放位置
    private int mPositionOnError = -1;

    // 播放模式
    private AudioPlayMode mPlayMode = AudioPlayMode.LOOP;
    // 播放速度
    private AudioPlaySpeed mPlaySpeed = AudioPlaySpeed.X_10;

    public AudioDelegate31(Context ctx) {
        mContext = ctx;
    }

    public void setPlayerType(AudioPlayerFactory.PlayerType type) {
        mPlayer = AudioPlayerFactory.get(type);
        mPlayer.setOnPreparedListener(mOnPreparedListener);
        mPlayer.setStatusBridge(new StatusBridge() {
            @Override
            public void playNextAudio() {
                nextAudio();
            }
            @Override
            public void saveStatusWhenError() {
                mPositionOnError = getCurrentPosition();
            }
        });
    }

    /**
     * 设置播放的mp3列表
     *
     * @param mp3List 播放的mp3列表
     */
    public void setMp3List(ArrayList<MP3> mp3List) {
        boolean isFirstSetData = (mMp3List == null);
        mMp3List = mp3List;
        // 添加切换拦截
        if(mPlayer.interceptSelect(mMp3List, 0)){
            return;
        }
        mCurrentMp3Index = 0;
        mPositionOnError = -1;
        if(!isFirstSetData){
            release();
        }
        recreate();
    }

    /**
     * 同步mp3列表
     */
    public void syncMp3List() {
        if (mMp3List == null || getMaxProgress() < 0) {
            return;
        }
        mPlayer.syncMp3List(mCurrentMp3Index,
                getMaxProgress(),
                getCurrentPosition(),
                mPlayMode.value(),
                mPlaySpeed.index());
    }

    /**
     * 设置播放模式
     *
     * @param audioPlayMode 播放模式
     */
    public void setPlayMode(AudioPlayMode audioPlayMode) {
        mPlayMode = audioPlayMode;
        mPlayer.callbackWhenModeSelect(audioPlayMode.value());
    }

    /**
     * 添加音频播放状态监听回调
     *
     * @param l 音频播放状态监听回调
     */
    public void addAudioStatusListener(AudioStatusListener l) {
        mPlayer.addAudioStatusListener(l);
    }

    /**
     * 移除音频播放状态监听回调
     *
     * @param l 音频播放状态监听回调
     */
    public void removeAudioStatusListener(AudioStatusListener l) {
        mPlayer.removeAudioStatusListener(l);
    }

    /**
     * 播放音频（继续播放or重新播放）
     */
    public void playAudio(){
        if(!mPlayer.available()){
            // 如果走到这里，说明状态异常，需要还原到初始状态重新播放
            selectAudio(mCurrentMp3Index);
            return;
        }
        if (!mPlayer.isPlaying()) {
            // 添加拦截
            if(mPlayer.interceptPlay()){
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
    public void pauseAudio() {
        if (mPlayer.available() && mPlayer.isPlaying()) {
            mPlayer.pause();
            // 添加暂停播放的回调
            mPlayer.callbackWhenPause();
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
     *
     * @param index 切换的音频索引
     */
    public void selectAudio(int index) {
        int nextIndex;
        if (index < 0) {
            // 第一首的上一首切换成最后一首
            nextIndex = mMp3List.size() - 1;
        } else if (index >= mMp3List.size()) {
            // 最后一首的下一首切换成第一首
            nextIndex = 0;
        } else {
            nextIndex = index;
        }
        release();
        // 添加切换拦截
        if(mPlayer.interceptSelect(mMp3List, nextIndex)){
            return;
        }
        if(nextIndex != mCurrentMp3Index){
            mPositionOnError = -1;
        }
        mCurrentMp3Index = nextIndex;
        recreate();
        start();
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
        if (mPlayer.available()) {
            mPlayer.release();
            // 每次重新播放，都需要手动再次获取url
            mMp3List.get(mCurrentMp3Index).audioUrl = "";
        }
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate() {
        if(mPlayer.available()){
            return;
        }
        mPlayer.init(mContext);
        Uri uri = mMp3List.get(mCurrentMp3Index).getAudioUri(mContext);
        setSpeed(mPlaySpeed);
        mPlayer.setDataSource(mContext, uri);
        // 添加准备开始的回调
        mPlayer.callbackWhenPrepareStart(mCurrentMp3Index);
    }

    private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(BaseAudioPlayer player) {
            // 选择或切换的回调
            mPlayer.callbackWhenSelect(mCurrentMp3Index);
            // 添加准备结束的回调
            mPlayer.callbackWhenPrepareEnd();
            if(mPositionOnError > 0){
                mPlayer.seekTo(mPositionOnError);
                mPositionOnError = -1;
            }
            initProgress();
        }
    };

    private void initProgress() {
        // 初始化播放位置
        mCurrentPosition = 1;
        // 开始不停地刷新播放进度
        mProgressRefreshHandler.sendEmptyMessage(0);
    }

    // 用于刷新播放进度的Handler
    private Handler mProgressRefreshHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取最新的播放位置
            int position = getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if (position != mCurrentPosition) {
                mCurrentPosition = position;
                // 播放位置的回调
                mPlayer.callbackWhenProgress(position);
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    /**
     * 播放音频
     */
    public void start() {
        if(mPlayer.available()){
            // 添加拦截
            if(mPlayer.interceptPlay()){
                return;
            }
            mPlayer.start();
            // 添加播放的回调
            mPlayer.callbackWhenPlay();
        }
    }

    /**
     * 获取当前播放的mp3文件索引
     *
     * @return 当前播放的mp3文件索引
     */
    public int getCurrentMp3Index() {
        return mCurrentMp3Index;
    }

    /**
     * 获取当前播放的mp3
     *
     * @return 当前播放的mp3
     */
    public MP3 getCurrentMp3() {
        return mMp3List.get(mCurrentMp3Index);
    }

    /**
     * 获取歌曲长度
     **/
    public int getMaxProgress() {
        if(!mPlayer.available()){
            return 0;
        }
        return mPlayer.getDuration();
    }

    /**
     * 获取播放位置
     */
    public int getCurrentPosition() {
        if(!mPlayer.available()){
            return mCurrentPosition;
        }
        return mPlayer.getCurrentPosition();
    }

    /**
     * 播放指定位置
     */
    public void seekToPosition(int msec) {
        if(mPlayer.available()){
            mPlayer.seekTo(msec);
        }
    }

    /**
     * 添加音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void addAudioSourceInterceptor(AudioSourceInterceptor i) {
        mPlayer.addAudioSourceInterceptor(i);
    }

    /**
     * 移除音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void removeAudioSourceInterceptor(AudioSourceInterceptor i) {
        mPlayer.removeAudioSourceInterceptor(i);
    }

    /**
     * 快退15秒
     */
    public void rewind() {
        int rewindPosition = mCurrentPosition - FORWARD_POSITION;
        if (rewindPosition < 0) {
            rewindPosition = 0;
        }
        if(mPlayer.available()){
            mPlayer.seekTo(rewindPosition);
        }
    }

    /**
     * 快进15秒
     */
    public void forward() {
        int forwardPosition = mCurrentPosition + FORWARD_POSITION;
        if (forwardPosition > getMaxProgress()) {
            forwardPosition = getMaxProgress();
        }
        if(mPlayer.available()){
            mPlayer.seekTo(forwardPosition);
        }
    }

    /**
     * 倍速播放功能
     *
     * @param audioPlaySpeed
     */
    public void setSpeed(AudioPlaySpeed audioPlaySpeed) {
        mPlaySpeed = audioPlaySpeed;
        mPlayer.setSpeed(audioPlaySpeed);
    }

    /**
     * 通知被拦截后的回调
     * @param index
     * @param flag
     */
    public void notifyIntercepted(int index, int flag){
        // 调用被拦截后的回调
        mPlayer.callbackWhenIntercepted(mMp3List, index, flag);
    }

}