package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate26;

public class Audio26Service extends Service {

    private AudioDelegate26 mAudioDelegate;
    private AudioNotifierDelegate26 mNotifierDelegate;
    private AudioSessionDelegate26 mSessionDelegate;
    private AudioFocusDelegate26 mFocusDelegate;
    private AudioNoisyDelegate26 mNoisyDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio26Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio26Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate26(this);
        mNotifierDelegate = new AudioNotifierDelegate26(this, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate26(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate26(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate26(this, mAudioDelegate);
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