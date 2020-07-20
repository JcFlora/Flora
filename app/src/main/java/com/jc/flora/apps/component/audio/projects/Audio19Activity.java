package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate19;

/**
 * Created by Samurai on 2018/1/7.
 */
public class Audio19Activity extends AppCompatActivity {

    private AudioDelegate19 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用ExoPlayer播放本地MP3");
        setContentView(R.layout.activity_audio3);
        mDelegate = new AudioDelegate19(this);
    }

    public void playAudio(View v){
        mDelegate.playAudio();
    }

    public void pauseAudio(View v){
        mDelegate.pauseAudio();
    }

    public void resetAudio(View v){
        mDelegate.startAudio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.stopAudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
    }
}