package com.jc.flora.apps.component.video.delegate;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by shijincheng on 2020/4/27.
 */
public class VideoFocusDelegate {

    private BaseVideoDelegate mVideoDelegate;
    private AudioManager mAudioManager;
    private boolean mIsPausedByFocusLossTransient;
    private int mVolumeWhenFocusLossTransientCanDuck;
    // 状态标记，标识是否正在播放，用来控制焦点处理
    private boolean mIsPlaying;

    public VideoFocusDelegate(Context context, BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mVideoDelegate.addVideoStatusListener(mVideoStatusListener);
        requestAudioFocus();
//        mVideoDelegate.addAudioSourceInterceptor(mAudioSourceInterceptor);
    }

    public void release() {
        mAudioManager.abandonAudioFocus(mFocusChangeListener);
    }

    private VideoStatusListener mVideoStatusListener = new VideoStatusListener(){
        @Override
        public void onPlay() {
            mIsPlaying = true;
        }

        @Override
        public void onPause() {
            mIsPlaying = false;
        }

        @Override
        public boolean onPlayIntercepted() {
            return !requestAudioFocus();
        }
    };

    private boolean requestAudioFocus() {
        return mAudioManager.requestAudioFocus(mFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
                == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private AudioManager.OnAudioFocusChangeListener mFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            int volume;
            switch (focusChange) {
                // 重新获得焦点
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (!mIsPlaying && mIsPausedByFocusLossTransient) {
                        // 通话结束，恢复播放
                        //mVideoDelegate.playVideo();
                    }
                    volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (mVolumeWhenFocusLossTransientCanDuck > 0 && volume == mVolumeWhenFocusLossTransientCanDuck / 2) {
                        // 恢复音量
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeWhenFocusLossTransientCanDuck,
                                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    }
                    mIsPausedByFocusLossTransient = false;
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
                        mIsPausedByFocusLossTransient = true;
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
            mVideoDelegate.pauseVideo();
        }
    }

}