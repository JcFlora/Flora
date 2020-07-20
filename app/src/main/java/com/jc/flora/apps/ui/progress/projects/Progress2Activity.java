package com.jc.flora.apps.ui.progress.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.jc.flora.R;
import com.jc.flora.apps.ui.progress.widget.RoundProgressBar;

/**
 * Created by Samurai on 2019/4/17.
 */
public class Progress2Activity extends AppCompatActivity {

    private RoundProgressBar mRoundProgressBar;
    private AppCompatSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("圆形进度条");
        setContentView(R.layout.activity_progress2);
        findViews();
        initViews();
    }

    private void findViews(){
        mRoundProgressBar = (RoundProgressBar)findViewById(R.id.rpb);
        mSeekBar = (AppCompatSeekBar)findViewById(R.id.sb_progress);
    }

    private void initViews() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRoundProgressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}