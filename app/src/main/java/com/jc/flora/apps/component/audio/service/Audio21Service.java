package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate20;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate20;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate20;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate21;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate20;

public class Audio21Service extends Service {

    private AudioDelegate20 mAudioDelegate;
    private AudioNotifierDelegate21 mNotifierDelegate;
    private AudioSessionDelegate20 mSessionDelegate;
    private AudioFocusDelegate20 mFocusDelegate;
    private AudioNoisyDelegate20 mNoisyDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate20(this);
        mNotifierDelegate = new AudioNotifierDelegate21(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate20(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate20(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate20(this, mAudioDelegate);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAudioDelegate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotifierDelegate.release();
        mSessionDelegate.release();
        mFocusDelegate.release();
        mNoisyDelegate.release();
        mAudioDelegate.release();
    }

}