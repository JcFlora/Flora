package com.jc.flora.apps.component.audio.delegate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jc.flora.R;
import com.jc.flora.apps.component.audio.adapter.AudioListAdapter2;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.audio.projects.AudioDetail24Activity;
import com.jc.flora.apps.component.audio.service.Audio24Service;
import com.jc.flora.apps.ui.progress.widget.RoundProgressBar;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/19.
 */

public class AudioListPlayerDelegate24 {

    // mp3列表
    private static final ArrayList<MP3> MP3_LIST = new ArrayList<MP3>() {
        {
            add(new MP3("童话镇", R.raw.audio_fairyland, R.drawable.audio_fairyland));
            add(new MP3("恋人心", R.raw.audio_lover_heart, R.drawable.audio_lover_heart));
            add(new MP3("半壶纱", R.raw.audio_gauze, R.drawable.audio_gauze));
            add(new MP3("帝都", R.raw.audio_capital, R.drawable.audio_capital));
            add(new MP3("萌二代", R.raw.audio_kawaii, R.drawable.audio_kawaii));
        }
    };

    private AppCompatActivity mActivity;

    private AudioDelegate24 mDelegate;
    // mp3列表
    private RecyclerView mRvAudioList;
    // 列表适配器
    private AudioListAdapter2 mAdapter;
    // 播放条
    private View mLayoutAudioBar;
    // 当前mp3音频封面图
    private ImageView mIvCover;
    // 当前mp3名称
    private TextView mTvName;
    // 播放进度条
    private SeekBar mSbProgress;
    // 播放按钮
    private View mBtnPlay;
    // 播放图标
    private ImageView mIvPlay;
    // 播放的圆形进度条
    private RoundProgressBar mPbPlay;
    // 状态标记，标识是否正在播放，用来控制播放按钮
    private boolean mIsPlaying;

    public AudioListPlayerDelegate24(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setLayoutAudioBar(View layoutAudioBar) {
        mLayoutAudioBar = layoutAudioBar;
    }

    public void setRvAudioList(RecyclerView rvAudioList) {
        mRvAudioList = rvAudioList;
    }

    public void setIvCover(ImageView ivCover) {
        mIvCover = ivCover;
    }

    public void setTvName(TextView tvName) {
        mTvName = tvName;
    }

    public void setSbProgress(SeekBar sbProgress) {
        mSbProgress = sbProgress;
    }

    public void setBtnPlay(View btnPlay) {
        mBtnPlay = btnPlay;
    }

    public void setIvPlay(ImageView ivPlay) {
        mIvPlay = ivPlay;
    }

    public void setPbPlay(RoundProgressBar pbPlay) {
        mPbPlay = pbPlay;
    }

    public void init() {
        initViews();
        initDelegate();
    }

    public void playAudio(int index){
        mDelegate.selectAudio(index);
    }

    public void release() {
        mDelegate.removeAudioStatusListener(mAudioStatusListener);
        mActivity.unbindService(mConnection);
    }

    private void initViews() {
        // 设置mp3列表数据展示
        mAdapter = new AudioListAdapter2(MP3_LIST);
        mRvAudioList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDelegate.selectAudio(position);
            }
        });
        // 设置播放条的点击跳转
        mLayoutAudioBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAudioDetail();
            }
        });
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿
                if (fromUser && mDelegate != null) {
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
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying) {
                    mDelegate.pauseAudio();
                } else {
                    mDelegate.playAudio();
                }
            }
        });
    }

    private void initDelegate() {
        Intent intent = new Intent(mActivity, Audio24Service.class);
        mActivity.bindService(intent, mConnection, Activity.BIND_AUTO_CREATE);
    }

    // 使用后台Service播放音频，界面和后台Service的连接对象，通过此对象进行通信
    private ServiceConnection mConnection = new ServiceConnection() {
        // 连接Service时回调，保存控制播放组件
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDelegate = (AudioDelegate24) service;
            mDelegate.addAudioStatusListener(mAudioStatusListener);
            mDelegate.setMp3List(MP3_LIST);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    // 之前的界面刷新功能改成监听回调实现，实现控制反转，由播放状态本身进行控制
    private AudioStatusListener mAudioStatusListener = new AudioStatusListener() {

        @Override
        public void onSelect(int index, int maxProgress) {
            // 设置当前音频封面图
            mIvCover.setImageResource(MP3_LIST.get(index).coverImgResId);
            // 设置当前音频名称
            mTvName.setText(MP3_LIST.get(index).name);
            // 设置当前音频播放最大进度值
            mSbProgress.setMax(maxProgress);
            mPbPlay.setMax(maxProgress);
            // 适配器同步索引和最大进度值
            mAdapter.setCurrentPlayIndexAndMax(index, maxProgress);
        }

        @Override
        public void onPlay() {
            mIsPlaying = true;
            mIvPlay.setImageResource(R.drawable.audio_bar_pause);
        }

        @Override
        public void onPause() {
            mIsPlaying = false;
            mIvPlay.setImageResource(R.drawable.audio_bar_play);
        }

        @Override
        public void onStop() {
            mIsPlaying = false;
            mIvPlay.setImageResource(R.drawable.audio_bar_play);
        }

        @Override
        public void onProgress(int progress) {
            // 设置当前进度值
            mSbProgress.setProgress(progress);
            mPbPlay.setProgress(progress);
            // 适配器同步当前进度值
            mAdapter.setCurrentProgress(mDelegate.getCurrentMp3Index(), progress);
        }

    };

    private void gotoAudioDetail(){
        mActivity.startActivity(new Intent(mActivity, AudioDetail24Activity.class));
    }

}