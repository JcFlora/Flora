package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/**
 * Created by shijincheng on 2018/1/7.
 */

public class AudioNoisyDelegate20 {

    private Service mService;
    private AudioDelegate20 mAudioDelegate;
    private boolean mIsReceiverRegistered = false;

    public AudioNoisyDelegate20(Service service, AudioDelegate20 audioDelegate) {
        mService = service;
        mAudioDelegate = audioDelegate;
        addAudioStatusListener();
    }

    private void addAudioStatusListener() {
        mAudioDelegate.addAudioStatusListener(mAudioStatusListener);
    }

    private AudioStatusListener mAudioStatusListener = new AudioStatusListener() {
        @Override
        public void onPlay() {
            registerAudioNoisyReceiver();
        }

        @Override
        public void onPause() {
            unregisterAudioNoisyReceiver();
        }
    };

    /**
     * 动态注册音频播放通道发生改变的广播监听，必须在播放状态时注册，
     * 这样就只会在播放时接收到广播
     */
    private void registerAudioNoisyReceiver(){
        if(!mIsReceiverRegistered){
            IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
            mService.registerReceiver(mAudioNoisyReceiver, intentFilter);
            mIsReceiverRegistered = true;
        }
    }

    /**
     * 动态反注册音频播放通道发生改变的广播监听，必须在暂停状态下反注册，
     * 这样在暂停时就不会接收到广播
     */
    private void unregisterAudioNoisyReceiver(){
        if(mIsReceiverRegistered){
            mService.unregisterReceiver(mAudioNoisyReceiver);
            mIsReceiverRegistered = false;
        }
    }

    private BroadcastReceiver mAudioNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            switch (intent.getAction()) {
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    mAudioDelegate.pauseAudio();
                    break;
                default:
                    break;
            }
        }
    };

    public void release() {
        //
        mAudioDelegate.removeAudioStatusListener(mAudioStatusListener);
        //
        unregisterAudioNoisyReceiver();
    }

}