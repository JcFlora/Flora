package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate16;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate16;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate16;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate16;

public class Audio16Service extends Service {

    private AudioDelegate16 mAudioDelegate;
    private AudioNotifierDelegate16 mNotifierDelegate;
    private AudioSessionDelegate16 mSessionDelegate;
    private AudioFocusDelegate16 mFocusDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate16(this);
        mNotifierDelegate = new AudioNotifierDelegate16(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate16(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate16(this, mAudioDelegate);
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
        mAudioDelegate.release();
    }

}