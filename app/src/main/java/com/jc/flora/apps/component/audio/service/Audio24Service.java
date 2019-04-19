package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate24;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate24;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate24;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate24;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate24;

public class Audio24Service extends Service {

    private AudioDelegate24 mAudioDelegate;
    private AudioNotifierDelegate24 mNotifierDelegate;
    private AudioSessionDelegate24 mSessionDelegate;
    private AudioFocusDelegate24 mFocusDelegate;
    private AudioNoisyDelegate24 mNoisyDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio24Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio24Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate24(this);
        mNotifierDelegate = new AudioNotifierDelegate24(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate24(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate24(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate24(this, mAudioDelegate);
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