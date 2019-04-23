package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate27;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate27;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate27;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate27;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate27;

public class Audio27Service extends Service {

    private AudioDelegate27 mAudioDelegate;
    private AudioNotifierDelegate27 mNotifierDelegate;
    private AudioSessionDelegate27 mSessionDelegate;
    private AudioFocusDelegate27 mFocusDelegate;
    private AudioNoisyDelegate27 mNoisyDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio27Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio27Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate27(this);
        mNotifierDelegate = new AudioNotifierDelegate27(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate27(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate27(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate27(this, mAudioDelegate);
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