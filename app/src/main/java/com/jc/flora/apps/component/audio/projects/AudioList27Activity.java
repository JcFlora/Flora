package com.jc.flora.apps.component.audio.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioListPlayerDelegate26;
import com.jc.flora.apps.component.audio.service.Audio27Service;
import com.jc.flora.apps.ui.progress.widget.RoundProgressBar;

/**
 * Created by Samurai on 2019/4/23.
 */
public class AudioList27Activity extends AppCompatActivity {

    private AudioListPlayerDelegate26 mDelegate;
    // mp3列表
    private RecyclerView mRvAudioList;
    // 播放条
    private View mLayoutAudioBar;
    // 当前mp3音频封面图
    private ImageView mIvAudioBarCover;
    // 当前mp3名称
    private TextView mTvAudioBarName;
    // 播放进度条
    private SeekBar mSbAudioBarProgress;
    // 播放按钮
    private View mBtnAudioBarPlay;
    // 播放图标
    private ImageView mIvAudioBarPlay;
    // 播放的圆形进度条
    private RoundProgressBar mPbAudioBarPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAudioService();
        setTitle("添加loading和缓冲回调处理");
        setContentView(R.layout.activity_audio_list23);
        findViews();
        initViews();
        initDelegate();
    }

    private void startAudioService(){
        // 实际项目中请在Application的onCreate方法中调用此方法
        Audio27Service.start(this);
    }

    private void findViews(){
        mRvAudioList = findViewById(R.id.rv_audio_list);
        mLayoutAudioBar = findViewById(R.id.layout_audio_bar);
        mIvAudioBarCover = findViewById(R.id.iv_audio_bar_cover);
        mTvAudioBarName = findViewById(R.id.tv_audio_bar_name);
        mSbAudioBarProgress = findViewById(R.id.sb_audio_bar_progress);
        mBtnAudioBarPlay = findViewById(R.id.btn_audio_bar_play);
        mIvAudioBarPlay = findViewById(R.id.iv_audio_bar_play);
        mPbAudioBarPlay = findViewById(R.id.pb_audio_bar_play);
    }

    private void initViews(){
        mRvAudioList.setLayoutManager(new LinearLayoutManager(this));
        mRvAudioList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initDelegate(){
        mDelegate = new AudioListPlayerDelegate26(this, Audio27Service.class, AudioDetail27Activity.class);
        mDelegate.setRvAudioList(mRvAudioList);
        mDelegate.setLayoutAudioBar(mLayoutAudioBar);
        mDelegate.setIvCover(mIvAudioBarCover);
        mDelegate.setTvName(mTvAudioBarName);
        mDelegate.setSbProgress(mSbAudioBarProgress);
        mDelegate.setBtnPlay(mBtnAudioBarPlay);
        mDelegate.setIvPlay(mIvAudioBarPlay);
        mDelegate.setPbPlay(mPbAudioBarPlay);
        mDelegate.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
        stopAudioService();
    }

    private void stopAudioService(){
        // 实际项目中请在应用退出时调用此方法
        Audio27Service.stop(this);
    }

}