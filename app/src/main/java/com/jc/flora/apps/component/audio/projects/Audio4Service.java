package com.jc.flora.apps.component.audio.projects;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate4;

public class Audio4Service extends Service {

    private AudioDelegate4 mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = new AudioDelegate4(this);
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