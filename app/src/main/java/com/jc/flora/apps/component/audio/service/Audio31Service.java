package com.jc.flora.apps.component.audio.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.delegate.AudioDelegate31;
import com.jc.flora.apps.component.audio.delegate.AudioFocusDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioMobileNetInterceptDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioNetInterceptDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioNoisyDelegate;
import com.jc.flora.apps.component.audio.delegate.AudioNotifierDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioSessionDelegate26;
import com.jc.flora.apps.component.audio.delegate.AudioSourceInterceptDelegate;
import com.jc.flora.apps.component.audio.delegate.BaseAudioDelegate;
import com.jc.flora.apps.component.audio.player.AudioPlayerFactory;
import com.jc.flora.apps.component.audio.projects.AudioDetail31Activity;

public class Audio31Service extends Service {

    private BaseAudioDelegate mAudioDelegate;
    private AudioNotifierDelegate26 mNotifierDelegate;
    private AudioSessionDelegate26 mSessionDelegate;
    private AudioFocusDelegate mFocusDelegate;
    private AudioNoisyDelegate mNoisyDelegate;
    private AudioNetInterceptDelegate mNetInterceptDelegate;
    private AudioMobileNetInterceptDelegate mMobileNetInterceptDelegate;
    private AudioSourceInterceptDelegate mSourceInterceptDelegate;

    /**
     * 启动当前Service，需要在bind之前调用
     * @param context
     */
    public static void start(Context context){
        context.startService(new Intent(context, Audio31Service.class));
    }

    /**
     * 停止当前Service，需要在所有unbind之后调用
     * @param context
     */
    public static void stop(Context context){
        context.stopService(new Intent(context, Audio31Service.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioDelegate = new AudioDelegate31(this);
        mAudioDelegate.setPlayerType(AudioPlayerFactory.PlayerType.EXO);
        mNotifierDelegate = new AudioNotifierDelegate26(this, AudioDetail31Activity.class, mAudioDelegate);
        mSessionDelegate = new AudioSessionDelegate26(this, mAudioDelegate);
        mFocusDelegate = new AudioFocusDelegate(this, mAudioDelegate);
        mNoisyDelegate = new AudioNoisyDelegate(this, mAudioDelegate);
        mNetInterceptDelegate = new AudioNetInterceptDelegate(this, mAudioDelegate);
        mMobileNetInterceptDelegate = new AudioMobileNetInterceptDelegate(this, mAudioDelegate);
        mSourceInterceptDelegate = new AudioSourceInterceptDelegate(mAudioDelegate);
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
        mNetInterceptDelegate.release();
        mMobileNetInterceptDelegate.release();
        mSourceInterceptDelegate.release();
        mAudioDelegate.release();
    }

}