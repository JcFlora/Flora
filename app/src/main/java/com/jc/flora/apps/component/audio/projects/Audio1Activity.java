package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate1;

/**
 * Created by Samurai on 2017/7/15.
 */
public class Audio1Activity extends AppCompatActivity {

    private AudioDelegate1 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用MediaPlayer播放本地MP3");
        setContentView(R.layout.activity_audio1);
        mDelegate = new AudioDelegate1(this);
    }

    public void startAudio(View v){
        mDelegate.startAudio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.release();
    }

}