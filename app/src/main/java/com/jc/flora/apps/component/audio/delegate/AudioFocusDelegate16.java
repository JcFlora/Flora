package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;

/**
 * Created by shijincheng on 2017/10/15.
 */

public class AudioFocusDelegate16 {

    private AudioDelegate13 mAudioDelegate;
    private AudioManager mAudioManager;
    private boolean isPausedByFocusLossTransient;
    private int mVolumeWhenFocusLossTransientCanDuck;
    // 状态标记，标识是否正在播放，用来控制播放按钮
    private boolean mIsPlaying;

    public AudioFocusDelegate16(Service service, AudioDelegate13 audioDelegate) {
        mAudioDelegate = audioDelegate;
        mAudioManager = (AudioManager) service.getSystemService(Context.AUDIO_SERVICE);
        addAudioStatusListener();
        //requestAudioFocus();
    }

    public void release() {
        mAudioDelegate.removeAudioStatusListener(mAudioStatusListener);
        mAudioManager.abandonAudioFocus(mFocusChangeListener);
    }

    private void addAudioStatusListener() {
        mAudioDelegate.addAudioStatusListener(mAudioStatusListener);
    }

    private boolean requestAudioFocus() {
        return mAudioManager.requestAudioFocus(mFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
                == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private AudioStatusListener mAudioStatusListener = new AudioStatusListener(){
        @Override
        public void onPlay() {
            mIsPlaying = true;
            if(!requestAudioFocus()){
                mAudioDelegate.pauseAudio();
            }
        }

        @Override
        public void onPause() {
            mIsPlaying = false;
        }
    };

    private AudioManager.OnAudioFocusChangeListener mFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            int volume;
            switch (focusChange) {
                // 重新获得焦点
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (!mIsPlaying && isPausedByFocusLossTransient) {
                        // 通话结束，恢复播放
                        mAudioDelegate.playAudio();
                    }
                    volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (mVolumeWhenFocusLossTransientCanDuck > 0 && volume == mVolumeWhenFocusLossTransientCanDuck / 2) {
                        // 恢复音量
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck,
                                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    }
                    isPausedByFocusLossTransient = false;
                    mVolumeWhenFocusLossTransientCanDuck = 0;
                    break;
                // 永久丢失焦点，如被其他播放器抢占
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (mIsPlaying) {
                        forceStop();
                    }
                    break;
                // 短暂丢失焦点，如来电
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mIsPlaying) {
                        forceStop();
                        isPausedByFocusLossTransient = true;
                    }
                    break;
                // 瞬间丢失焦点，如通知
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // 音量减小为一半
                    volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (mIsPlaying && volume > 0) {
                        mVolumeWhenFocusLossTransientCanDuck = volume;
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck / 2,
                                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    }
                    break;
            }
        }
    };

    private void forceStop() {
        if (mIsPlaying) {
            mAudioDelegate.pauseAudio();
        }
    }

}