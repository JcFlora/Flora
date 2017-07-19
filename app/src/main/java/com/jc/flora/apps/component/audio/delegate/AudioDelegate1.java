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

    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public AudioDelegate1(Context ctx) {
        mContext = ctx;
    }

    public void startAudio(){
        release();
        recreate();
        start();
    }

    public void release(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void recreate(){
        if(mMediaPlayer == null){
            // 在振动的同时播放背景音
            mMediaPlayer = MediaPlayer.create(mContext, AUDIO_ID);
            // 不循环
            mMediaPlayer.setLooping(false);
        }
    }

    public void start(){
        mMediaPlayer.start();
    }

}