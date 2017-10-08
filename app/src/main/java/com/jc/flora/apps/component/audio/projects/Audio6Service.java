package com.jc.flora.apps.component.audio.projects;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate6;

public class Audio6Service extends Service {

    private AudioDelegate6 mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = new AudioDelegate6(this);
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