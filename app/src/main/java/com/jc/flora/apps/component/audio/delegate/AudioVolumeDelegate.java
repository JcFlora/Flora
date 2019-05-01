package com.jc.flora.apps.component.audio.delegate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

/**
 * Created by shijincheng on 2017/10/22.
 */

public class AudioVolumeDelegate {

    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

    private AppCompatActivity mActivity;
    // 音量进度条
    private SeekBar mSbVolume;

    private AudioManager mAudioManager;

    public AudioVolumeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setSbVolume(SeekBar sbVolume) {
        mSbVolume = sbVolume;
    }

    public void init(){
        mAudioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        initViews();
        registerAudioVolumeReceiver();
    }

    public void destroy(){
        mActivity.unregisterReceiver(mVolumeReceiver);
    }

    private void initViews() {
        mSbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mSbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        mSbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
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

    private void registerAudioVolumeReceiver(){
        IntentFilter filter = new IntentFilter(VOLUME_CHANGED_ACTION);
        mActivity.registerReceiver(mVolumeReceiver, filter);
    }

    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(mSbVolume != null){
                mSbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        }
    };

}
