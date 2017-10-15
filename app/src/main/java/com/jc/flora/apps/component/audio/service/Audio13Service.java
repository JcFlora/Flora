package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate13;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate13;

public class Audio13Service extends Service {

    private AudioDelegate13 mAudioDelegate;
    private AudioNotifierDelegate13 mNotifierDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate13(this);
        mNotifierDelegate = new AudioNotifierDelegate13(this, mAudioDelegate);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAudioDelegate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotifierDelegate.release();
        mAudioDelegate.release();
    }

}