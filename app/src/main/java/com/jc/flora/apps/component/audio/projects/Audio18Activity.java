package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioPlayerDelegate13;
import com.jc.flora.apps.component.audio.delegate.AudioVolumeDelegate;
import com.jc.flora.apps.component.audio.service.Audio17Service;

/**
 * Created by Samurai on 2017/10/22.
 */
public class Audio18Activity extends AppCompatActivity {

    private AudioVolumeDelegate mVolumeDelegate;
    private AudioPlayerDelegate13 mDelegate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("实现界面上的音量控制");
        setContentView(R.layout.activity_audio18);
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
    }

    private void initDelegate(){
        mVolumeDelegate = new AudioVolumeDelegate(this);
        mVolumeDelegate.setSbVolume(mSbVolume);
        mVolumeDelegate.init();

        mDelegate = new AudioPlayerDelegate13(this, Audio17Service.class);
        mDelegate.setIvCover(mIvCover);
        mDelegate.setTvCurrentTime(mTvCurrentTime);
        mDelegate.setSbProgress(mSbProgress);
        mDelegate.setTvMaxTime(mTvMaxTime);
        mDelegate.setBtnMode(mBtnMode);
        mDelegate.setBtnPre(mBtnPre);
        mDelegate.setBtnPlay(mBtnPlay);
        mDelegate.setBtnNext(mBtnNext);
        mDelegate.setBtnSelect(mBtnSelect);
        mDelegate.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVolumeDelegate.destroy();
        mDelegate.release();
    }

}