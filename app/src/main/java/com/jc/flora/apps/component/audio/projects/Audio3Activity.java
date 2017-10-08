package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate3;

/**
 * Created by Samurai on 2017/10/5.
 */
public class Audio3Activity extends AppCompatActivity {

    private AudioDelegate3 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支持暂停和继续播放");
        setContentView(R.layout.activity_audio3);
        AudioDelegate3.bindAudioDelegate(this, new AudioDelegate3.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate3 delegate) {
                mDelegate = delegate;
            }
        });
    }

    public void playAudio(View v){
        mDelegate.playAudio();
    }

    public void pauseAudio(View v){
        mDelegate.pauseAudio();
    }

    public void resetAudio(View v){
        mDelegate.resetAudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
        mDelegate.unbind(this);
    }

}