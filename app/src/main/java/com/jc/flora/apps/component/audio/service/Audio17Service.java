package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate16;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate14;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate15;
import com.jc.flora.apps.component.audio.delegate.BaseAudioDelegate;
import com.jc.flora.apps.component.audio.projects.Audio17Activity;

public class Audio17Service extends Service {

    private BaseAudioDelegate mAudioDelegate;
    private AudioNotifierDelegate14 mNotifierDelegate;
    private AudioSessionDelegate15 mSessionDelegate;
    private AudioFocusDelegate mFocusDelegate;
    private AudioNoisyDelegate mNoisyDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate16(this);
        mNotifierDelegate = new AudioNotifierDelegate14(this, Audio17Activity.class, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate15(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate(this, mAudioDelegate);
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