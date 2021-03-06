package com.jc.flora.apps.component.audio.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate10;
import com.jc.flora.apps.component.audio.delegate.AudioPlayMode;
import com.jc.flora.apps.component.audio.delegate.AudioStatusListener;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Samurai on 2017/10/10.
 */
public class Audio11Activity extends AppCompatActivity {

    //进度条下面的当前进度文字，将毫秒化为mm:ss格式
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());
    //播放模式名称列表
    private static final String[] MODE_ITEMS = {"列表循环", "单曲循环", "随机播放"};
    //播放模式图标
    private static final int[] MODE_ICON_RES_IDS = {R.drawable.audio_loop, R.drawable.audio_single, R.drawable.audio_shuffle};

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

    private AudioDelegate10 mDelegate;
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
    // 状态标记，标识是否正在播放，用来控制播放按钮
    private boolean mIsPlaying;
    // 下一个播放模式
    private AudioPlayMode mNextMode = AudioPlayMode.SINGLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("经典控制播放按钮");
        setContentView(R.layout.activity_audio11);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
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

    private void initViews(){
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿
                if(fromUser && mDelegate != null){
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
        mBtnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.setPlayMode(mNextMode);
            }
        });
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsPlaying){
                    mDelegate.pauseAudio();
                }else{
                    mDelegate.playAudio();
                }
            }
        });
        mBtnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.preAudio();
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.nextAudio();
            }
        });
        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMp3ListDialog();
            }
        });
    }

    private void initDelegate(){
        AudioDelegate10.bindAudioDelegate(this, new AudioDelegate10.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate10 delegate) {
                mDelegate = delegate;
                mDelegate.setAudioStatusListener(mAudioStatusListener);
                mDelegate.setMp3List(MP3_LIST);
            }
        });
    }

    // 之前的界面刷新功能改成监听回调实现，实现控制反转，由播放状态本身进行控制
    private AudioStatusListener mAudioStatusListener = new AudioStatusListener(){

        @Override
        public void onModeSelect(int index) {
            mBtnMode.setImageResource(MODE_ICON_RES_IDS[index]);
            ToastDelegate.show(Audio11Activity.this, MODE_ITEMS[index]);
            mNextMode = AudioPlayMode.valueOf((++index)%3);
        }

        @Override
        public void onSelect(int index, int maxProgress) {
            // 设置当前音频封面图
            mIvCover.setImageResource(MP3_LIST.get(mDelegate.getCurrentMp3Index()).coverImgResId);
            // 设置当前音频播放最大进度值
            mSbProgress.setMax(maxProgress);
            // 设置当前音频总时间
            mTvMaxTime.setText(FORMAT.format(maxProgress));
        }

        @Override
        public void onPlay() {
            mIsPlaying = true;
            mBtnPlay.setImageResource(R.drawable.audio_pause);
        }

        @Override
        public void onPause() {
            mIsPlaying = false;
            mBtnPlay.setImageResource(R.drawable.audio_play);
        }

        @Override
        public void onStop() {
            mIsPlaying = false;
            mBtnPlay.setImageResource(R.drawable.audio_play);
        }

        @Override
        public void onProgress(int progress) {
            // 设置当前进度值
            mSbProgress.setProgress(progress);
            // 设置时间
            mTvCurrentTime.setText(FORMAT.format(progress));
        }

    };

    private void showMp3ListDialog() {
        final String[] ITEMS = {"童话镇", "恋人心", "半壶纱", "帝都", "萌二代"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择歌曲");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDelegate.selectAudio(which);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.setAudioStatusListener(null);
        mDelegate.unbind(this);
    }

}