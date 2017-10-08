package com.jc.flora.apps.component.audio.projects;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate3;

public class Audio3Service extends Service {

    private AudioDelegate3 mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = new AudioDelegate3(this);
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