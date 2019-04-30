package com.jc.flora.apps.component.audio.delegate;

import android.app.Activity;
import android.app.Service;
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
import com.jc.flora.apps.component.audio.adapter.AudioListAdapter3;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.ui.progress.widget.RoundProgressBar;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/22.
 */

public class AudioListPlayerDelegate26 {

    // mp3列表
    private static final ArrayList<MP3> MP3_LIST = new ArrayList<MP3>() {
        {
            add(new MP3(
                    "话说明朝",
                    "http://imagev2.xmcdn.com/group29/M0A/EF/E3/wKgJWVk9_IzwKhYSAALu_VmdTNU780.jpg",
                    "http://fdfs.xmcdn.com/group33/M08/D9/53/wKgJTFmlRnbh_TYKAI7ECmC5Ar0312.mp3"));
            add(new MP3(
                    "百思女神秀",
                    "http://imagev2.xmcdn.com/group24/M03/B0/DF/wKgJMFiv-vDTK5vMAAF7QprBHfM982.jpg",
                    "http://audio.xmcdn.com/group28/M08/53/44/wKgJXFlolI-h7V3tAJv65WyL-2w446.mp3"));
            add(new MP3("欢乐江湖",
                    "http://imagev2.xmcdn.com/group31/M08/C0/C1/wKgJSVl6t6yz7Uc_AAPh7XreoIY059.jpg",
                    "http://audio.xmcdn.com/group60/M06/D4/23/wKgLeVy2irnRvgn8AIajOcTEqmw907.mp3"));
            add(new MP3(
                    "万万妹想到",
                    "http://imagev2.xmcdn.com/group46/M06/1D/E6/wKgKj1tzoYvT2vTaAAG4z2Hwi0o546.jpg",
                    "http://aod.tx.xmcdn.com/group58/M0B/0F/24/wKgLc1y5nOzgvS3PAKoJOL7U_s4990.mp3"));
            add(new MP3("爆笑相声",
                    "http://fdfs.xmcdn.com/group34/M05/CD/8A/wKgJYVntnamR9f32AAfwumDq4eM178_mobile_large.jpg",
                    "http://audio.xmcdn.com/group42/M08/93/BA/wKgJ81qY_HPC8A5_AGtb0aEwncA343.mp3"));
            add(new MP3("每天一个心理知识",
                    "http://fdfs.xmcdn.com/group41/M02/09/B6/wKgJ8lqTzluhjBw7AAGHo1SIL8A073_mobile_large.jpg",
                    "http://aod.tx.xmcdn.com/group58/M01/5B/AA/wKgLc1y9ZZSwIEI_ABX7_5NHUIE189.mp3"));
            add(new MP3("世界名人英文演讲",
                    "http://imagev2.xmcdn.com/group44/M06/47/DA/wKgKkVsPo6uB84WPABR_5iGVCX8204.png",
                    "http://audio.xmcdn.com/group59/M07/60/DD/wKgLely9mLuDau_4ACwyApwo5rI031.mp3"));
            add(new MP3("悦读心时光",
                    "http://imagev2.xmcdn.com/group21/M06/42/D0/wKgJLVs10eTi3kEcAAFR5Cec3Bc569.jpg",
                    "http://aod.tx.xmcdn.com/group56/M05/11/3A/wKgLdlxQY2OC8NZcAEssQdU-UDA049.mp3"));
        }
    };

    private AppCompatActivity mActivity;
    private Class<? extends Service> mServiceClass;
    private Class<? extends AppCompatActivity> mDetailActivityClass;

    private BaseAudioDelegate mDelegate;
    // mp3列表
    private RecyclerView mRvAudioList;
    // 列表适配器
    private AudioListAdapter3 mAdapter;
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

    public AudioListPlayerDelegate26(AppCompatActivity activity, Class<? extends Service> serviceClass, Class<? extends AppCompatActivity> detailActivityClass) {
        mActivity = activity;
        mServiceClass = serviceClass;
        mDetailActivityClass = detailActivityClass;
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
        mAdapter = new AudioListAdapter3(MP3_LIST);
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
        Intent intent = new Intent(mActivity, mServiceClass);
        mActivity.bindService(intent, mConnection, Activity.BIND_AUTO_CREATE);
    }

    // 使用后台Service播放音频，界面和后台Service的连接对象，通过此对象进行通信
    private ServiceConnection mConnection = new ServiceConnection() {
        // 连接Service时回调，保存控制播放组件
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDelegate = (BaseAudioDelegate) service;
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
            MP3_LIST.get(index).loadAlbum(mIvCover);
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
        if(mDetailActivityClass != null){
            mActivity.startActivity(new Intent(mActivity, mDetailActivityClass));
        }
    }

}