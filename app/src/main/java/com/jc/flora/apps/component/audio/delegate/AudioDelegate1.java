package com.jc.flora.apps.component.audio.delegate;

import android.content.Context;
import android.media.MediaPlayer;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/7/17.
 */
public class AudioDelegate1 {

    // 声音资源
    private static final int AUDIO_ID = R.raw.audio_kongfu;
    // 上下文
    private Context mContext;
    // 播放音频的核心组件MediaPlayer
    private MediaPlayer mMediaPlayer;

    public AudioDelegate1(Context ctx) {
        mContext = ctx;
    }

    /**
     * 播放音频（从头开始播放）
     */
    public void startAudio(){
        release();
        recreate();
        start();
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate(){
        if(mMediaPlayer == null){
            // 播放声音
            mMediaPlayer = MediaPlayer.create(mContext, AUDIO_ID);
            if(mMediaPlayer != null){
                // 不循环
                mMediaPlayer.setLooping(false);
            }
        }
    }

    /**
     * 播放音频
     */
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

}