package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate20;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate16;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate17;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate14;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate15;
import com.jc.flora.apps.component.audio.delegate.BaseAudioDelegate;
import com.jc.flora.apps.component.audio.projects.AudioList21Activity;

public class Audio21Service extends Service {

    private BaseAudioDelegate mAudioDelegate;
    private AudioNotifierDelegate14 mNotifierDelegate;
    private AudioSessionDelegate15 mSessionDelegate;
    private AudioFocusDelegate16 mFocusDelegate;
    private AudioNoisyDelegate17 mNoisyDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate20(this);
        mNotifierDelegate = new AudioNotifierDelegate14(this, AudioList21Activity.class, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate15(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate16(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate17(this, mAudioDelegate);
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