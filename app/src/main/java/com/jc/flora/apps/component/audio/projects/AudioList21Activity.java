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
import com.jc.flora.apps.component.audio.delegate.AudioListPlayerDelegate21;
import com.jc.flora.apps.component.audio.service.Audio21Service;

/**
 * Created by Samurai on 2019/3/21.
 */
public class AudioList21Activity extends AppCompatActivity {

    private AudioListPlayerDelegate21 mDelegate;
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
    private ImageView mBtnAudioBarPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("列表页面播放本地MP3");
        setContentView(R.layout.activity_audio_list21);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mRvAudioList = findViewById(R.id.rv_audio_list);
        mLayoutAudioBar = findViewById(R.id.layout_audio_bar);
        mIvAudioBarCover = findViewById(R.id.iv_audio_bar_cover);
        mTvAudioBarName = findViewById(R.id.tv_audio_bar_name);
        mSbAudioBarProgress = findViewById(R.id.sb_audio_bar_progress);
        mBtnAudioBarPlay = findViewById(R.id.btn_audio_bar_play);
    }

    private void initViews(){
        mRvAudioList.setLayoutManager(new LinearLayoutManager(this));
        mRvAudioList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initDelegate(){
        mDelegate = new AudioListPlayerDelegate21(this, Audio21Service.class, null);
        mDelegate.setRvAudioList(mRvAudioList);
        mDelegate.setLayoutAudioBar(mLayoutAudioBar);
        mDelegate.setIvCover(mIvAudioBarCover);
        mDelegate.setTvName(mTvAudioBarName);
        mDelegate.setSbProgress(mSbAudioBarProgress);
        mDelegate.setBtnPlay(mBtnAudioBarPlay);
        mDelegate.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
    }

}