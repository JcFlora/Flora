package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate2;

/**
 * Created by Samurai on 2017/10/4.
 */
public class Audio2Activity extends AppCompatActivity {

    private AudioDelegate2 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("后台Service播放本地MP3");
        setContentView(R.layout.activity_audio1);
        AudioDelegate2.bindAudioDelegate(this, new AudioDelegate2.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate2 delegate) {
                mDelegate = delegate;
            }
        });
    }

    public void startAudio(View v){
        mDelegate.startAudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
        mDelegate.unbind(this);
    }

}