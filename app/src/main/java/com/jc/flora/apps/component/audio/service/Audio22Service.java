package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate22;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate22;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate22;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate22;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate22;

public class Audio22Service extends Service {

    private AudioDelegate22 mAudioDelegate;
    private AudioNotifierDelegate22 mNotifierDelegate;
    private AudioSessionDelegate22 mSessionDelegate;
    private AudioFocusDelegate22 mFocusDelegate;
    private AudioNoisyDelegate22 mNoisyDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio22Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio22Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate22(this);
        mNotifierDelegate = new AudioNotifierDelegate22(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate22(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate22(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate22(this, mAudioDelegate);
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