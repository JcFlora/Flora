package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate12;

public class Audio12Service extends Service {

    private AudioDelegate12 mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = new AudioDelegate12(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDelegate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegate.release();
    }

}