package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate28;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate28;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate28;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate28;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate28;
import com.jc.flora.apps.component.audio.delegate.AudioSourceInterceptDelegate28;

public class Audio28Service extends Service {

    private AudioDelegate28 mAudioDelegate;
    private AudioNotifierDelegate28 mNotifierDelegate;
    private AudioSessionDelegate28 mSessionDelegate;
    private AudioFocusDelegate28 mFocusDelegate;
    private AudioNoisyDelegate28 mNoisyDelegate;
    private AudioSourceInterceptDelegate28 mSourceInterceptDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio28Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio28Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate28(this);
        mNotifierDelegate = new AudioNotifierDelegate28(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate28(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate28(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate28(this, mAudioDelegate);
        mSourceInterceptDelegate = new AudioSourceInterceptDelegate28(mAudioDelegate);
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
        mSourceInterceptDelegate.release();
        mAudioDelegate.release();
    }

}