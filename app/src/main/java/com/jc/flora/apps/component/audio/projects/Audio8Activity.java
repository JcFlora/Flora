package com.jc.flora.apps.component.audio.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.delegate.AudioDelegate8;
import com.jc.flora.apps.component.audio.delegate.AudioStatusListener;
import com.jc.flora.apps.component.audio.model.MP3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Samurai on 2017/10/9.
 */
public class Audio8Activity extends AppCompatActivity {

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

    private AudioDelegate8 mDelegate;
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
        setTitle("添加状态监听实现控制反转");
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
        AudioDelegate8.bindAudioDelegate(this, new AudioDelegate8.DelegateBuilder() {
            @Override
            public void onBind(AudioDelegate8 delegate) {
                mDelegate = delegate;
                mDelegate.setAudioStatusListener(mAudioStatusListener);
                mDelegate.setMp3List(MP3_LIST);
            }
        });
    }

    // 之前的界面刷新功能改成监听回调实现，实现控制反转，由播放状态本身进行控制
    private AudioStatusListener mAudioStatusListener = new AudioStatusListener(){

        @Override
        public void onSelect(int index, int maxProgress) {
            // 设置当前音频名称
            mTvName.setText("当前歌曲："+MP3_LIST.get(mDelegate.getCurrentMp3Index()).name);
            // 设置当前音频播放最大进度值
            mSbProgress.setMax(maxProgress);
            // 设置当前音频总时间
            mMaxTime = FORMAT.format(maxProgress);
        }

        @Override
        public void onProgress(int progress) {
            // 设置当前进度值
            mSbProgress.setProgress(progress);
            // 设置时间
            mTvTime.setText(FORMAT.format(progress) + "/" + mMaxTime);
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
        mDelegate.preAudio();
    }

    public void nextAudio(View v){
        mDelegate.nextAudio();
    }

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