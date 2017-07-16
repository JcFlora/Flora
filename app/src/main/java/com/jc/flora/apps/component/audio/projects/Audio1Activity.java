package com.jc.flora.apps.component.audio.projects;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/7/15.
 */
public class Audio1Activity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用MediaPlayer播放本地MP3");
        setContentView(R.layout.activity_audio1);
    }

    public void startAudio(View v){
        release();
        reCreate();
        start();
    }

    private void release(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void reCreate(){
        if(mMediaPlayer == null){
            // 在振动的同时播放背景音
            mMediaPlayer = MediaPlayer.create(this, R.raw.audio_kongfu);
            // 不循环
            mMediaPlayer.setLooping(false);
        }
    }

    private void start(){
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
    }

}