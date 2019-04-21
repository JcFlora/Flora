package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate25;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate25;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate25;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate25;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate25;

public class Audio25Service extends Service {

    private AudioDelegate25 mAudioDelegate;
    private AudioNotifierDelegate25 mNotifierDelegate;
    private AudioSessionDelegate25 mSessionDelegate;
    private AudioFocusDelegate25 mFocusDelegate;
    private AudioNoisyDelegate25 mNoisyDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio25Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio25Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate25(this);
        mNotifierDelegate = new AudioNotifierDelegate25(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate25(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate25(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate25(this, mAudioDelegate);
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