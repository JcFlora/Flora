package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDetailPlayerDelegate30;
import com.jc.flora.apps.component.audio.delegate.AudioVolumeDelegate;
import com.jc.flora.apps.component.audio.service.Audio30Service;

/**
 * Created by Samurai on 2019/4/25.
 */
public class AudioDetail30Activity extends AppCompatActivity {

    private AudioVolumeDelegate mVolumeDelegate;
    private AudioDetailPlayerDelegate30 mDelegate;
    // 音量进度条
    private SeekBar mSbVolume;
    // 当前mp3音频封面图
    private ImageView mIvCover;
    // 当前播放进度时间显示
    private TextView mTvCurrentTime;
    // 播放进度条
    private SeekBar mSbProgress;
    // 总进度时间显示
    private TextView mTvMaxTime;
    // 当前播放模式
    private ImageView mBtnMode;
    // 上一首按钮
    private ImageView mBtnPre;
    // 播放按钮
    private ImageView mBtnPlay;
    // 下一首按钮
    private ImageView mBtnNext;
    // 下一首按钮
    private ImageView mBtnSelect;
    // 快退15秒按钮
    private View mBtnRewind;
    // 快进15秒按钮
    private View mBtnForward;
    // 倍速按钮
    private TextView mBtnSpeed;
    // 播放Loading框
    private ProgressBar mPbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加移动网络检测与音频容量显示功能");
        setContentView(R.layout.activity_audio_detail27);
        findViews();
        initDelegate();
    }

    private void findViews(){
        mSbVolume = (SeekBar) findViewById(R.id.sb_volume);
        mIvCover = (ImageView) findViewById(R.id.iv_cover);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        mBtnMode = (ImageView) findViewById(R.id.btn_mode);
        mBtnPre = (ImageView) findViewById(R.id.btn_pre);
        mBtnPlay = (ImageView) findViewById(R.id.btn_play);
        mBtnNext = (ImageView) findViewById(R.id.btn_next);
        mBtnSelect = (ImageView) findViewById(R.id.btn_select);
        mBtnRewind = findViewById(R.id.btn_rewind);
        mBtnForward = findViewById(R.id.btn_forward);
        mBtnSpeed = (TextView) findViewById(R.id.btn_speed);
        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
    }

    private void initDelegate(){
        mVolumeDelegate = new AudioVolumeDelegate(this);
        mVolumeDelegate.setSbVolume(mSbVolume);
        mVolumeDelegate.init();

        mDelegate = new AudioDetailPlayerDelegate30(this, Audio30Service.class);
        mDelegate.setIvCover(mIvCover);
        mDelegate.setTvCurrentTime(mTvCurrentTime);
        mDelegate.setSbProgress(mSbProgress);
        mDelegate.setTvMaxTime(mTvMaxTime);
        mDelegate.setBtnMode(mBtnMode);
        mDelegate.setBtnPre(mBtnPre);
        mDelegate.setBtnPlay(mBtnPlay);
        mDelegate.setBtnNext(mBtnNext);
        mDelegate.setBtnSelect(mBtnSelect);
        mDelegate.setBtnRewind(mBtnRewind);
        mDelegate.setBtnForward(mBtnForward);
        mDelegate.setBtnSpeed(mBtnSpeed);
        mDelegate.setPbLoading(mPbLoading);
        mDelegate.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVolumeDelegate.destroy();
        mDelegate.release();
    }

}