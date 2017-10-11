package com.jc.flora.apps.component.audio.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate7;
import com.jc.flora.apps.component.audio.model.MP3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Samurai on 2017/10/8.
 */
public class Audio7Activity extends AppCompatActivity {

    //进度条下面的当前进度文字，将毫秒化为mm:ss格式
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());

    // mp3列表
    private static final ArrayList<MP3> MP3_LIST = new ArrayList<MP3>() {
        {
            add(new MP3("童话镇", R.raw.audio_fairyland));
            add(new MP3("恋人心", R.raw.audio_lover_heart));
            add(new MP3("半壶纱", R.raw.audio_gauze));
            add(new MP3("帝都", R.raw.audio_capital));
            add(new MP3("萌二代", R.raw.audio_kawaii));
        }
    };

    private AudioDelegate7 mDelegate;
    // 当前mp3歌曲名称
    private TextView mTvName;
    // 播放进度条
    private SeekBar mSbProgress;
    // 播放进度时间显示
    private TextView mTvTime;
    // 播放总时间
    private String mMaxTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("实现列表循环播放");
        setContentView(R.layout.activity_audio6);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mTvName = (TextView) findViewById(R.id.tv_name);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvTime = (TextView) findViewById(R.id.tv_time);
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
    }

    private void initDelegate(){
        AudioDelegate7.bindAudioDelegate(this, new AudioDelegate7.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate7 delegate) {
                mDelegate = delegate;
                mDelegate.setMp3List(MP3_LIST);
                initProgress();
            }
        });
    }

    private void initProgress() {
        // 初始化播放最大进度值
        mSbProgress.setMax(mDelegate.getMaxProgress());
        // 初始化总时间
        mMaxTime = FORMAT.format(mDelegate.getMaxProgress());
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

    public void selectAudio(View v){
        showMp3ListDialog();
    }

    public void playAudio(View v){
        mDelegate.playAudio();
    }

    public void pauseAudio(View v){
        mDelegate.pauseAudio();
    }

    public void resetAudio(View v){
        mDelegate.resetAudio();
    }

    public void preAudio(View v){
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
        mDelegate.preAudio();
        mTvName.setText("当前歌曲："+MP3_LIST.get(mDelegate.getCurrentMp3Index()).name);
        initProgress();
    }

    public void nextAudio(View v){
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
        mDelegate.nextAudio();
        mTvName.setText("当前歌曲："+MP3_LIST.get(mDelegate.getCurrentMp3Index()).name);
        initProgress();
    }

    private void showMp3ListDialog() {
        final String[] ITEMS = {"童话镇", "恋人心", "半壶纱", "帝都", "萌二代"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择歌曲");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectAudio(which);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void selectAudio(int index){
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
        mDelegate.selectAudio(index);
        mTvName.setText("当前歌曲："+MP3_LIST.get(index).name);
        initProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.release();
        mDelegate.unbind(this);
        mProgressRefreshHandler.removeCallbacksAndMessages(null);
    }

}