package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate4;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Samurai on 2017/10/6.
 */
public class Audio4Activity extends AppCompatActivity {

    //进度条下面的当前进度文字，将毫秒化为mm:ss格式
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());

    private AudioDelegate4 mDelegate;
    // 播放进度条
    private SeekBar mSbProgress;
    // 播放进度时间显示
    private TextView mTvTime;
    // 播放总时间
    private String mMaxTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支持进度显示和控制");
        setContentView(R.layout.activity_audio4);
        findViews();
        initDelegate();
    }

    private void findViews(){
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvTime = (TextView) findViewById(R.id.tv_time);
    }

    private void initDelegate(){
        AudioDelegate4.bindAudioDelegate(this, new AudioDelegate4.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate4 delegate) {
                mDelegate = delegate;
                initProgress();
            }
        });
    }

    private void initProgress() {
        // 初始化播放最大进度值
        mSbProgress.setMax(mDelegate.getMaxProgress());
        // 初始化总时间
        mMaxTime = FORMAT.format(mDelegate.getMaxProgress());
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿卡顿
                if(fromUser){
                    mDelegate.seekToPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // 开始不停地刷新播放进度
        mProgressRefreshHandler.sendEmptyMessage(0);
    }

    // 用于刷新播放进度的Handler
    private Handler mProgressRefreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSbProgress.setProgress(mDelegate.getCurrentPosition());
            String currentTime = FORMAT.format(mDelegate.getCurrentPosition());
            mTvTime.setText(currentTime + "/" + mMaxTime);
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

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
        mDelegate.unbind(this);
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

}