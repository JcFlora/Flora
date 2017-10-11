package com.jc.flora.apps.component.audio.delegate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.service.Audio4Service;

/**
 * Created by Samurai on 2017/10/6.
 */
public class AudioDelegate4 extends Binder {

    // 声音资源
    private static final int AUDIO_ID = R.raw.audio_fairyland;
    // 上下文
    private Context mContext;
    // 播放音频的核心组件MediaPlayer
    private MediaPlayer mMediaPlayer;
    // 使用后台Service播放音频，界面和后台Service的连接对象，通过此对象进行通信
    private ServiceConnection mConnection;

    /**
     * 绑定音频播放组件，该方法会绑定后台播放音频的Service
     * @param activity 控制音频播放的界面
     * @param builder  返回控制组件的回调接口，通过该接口获取当前组件，可以进行播放的控制
     */
    public static void bindAudioDelegate(Activity activity, final DelegateBuilder builder) {
        Intent intent = new Intent(activity, Audio4Service.class);
        activity.bindService(intent, new AudioServiceConnection(builder), Activity.BIND_AUTO_CREATE);
    }

    public AudioDelegate4(Context ctx) {
        mContext = ctx;
    }

    /**
     * 播放音频（继续播放）
     */
    public void playAudio(){
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    /**
     * 暂停播放
     */
    public void pauseAudio(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    /**
     * 复位（从头开始播放）
     */
    public void resetAudio() {
        release();
        recreate();
        start();
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate() {
        if (mMediaPlayer == null) {
            // 播放声音
            mMediaPlayer = MediaPlayer.create(mContext, AUDIO_ID);
            if (mMediaPlayer != null) {
                // 不循环
                mMediaPlayer.setLooping(false);
            }
        }
    }

    /**
     * 播放音频
     */
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    /**
     * 解绑后台播放音频的Service，在页面销毁后调用
     * @param activity 控制音频播放的界面
     */
    public void unbind(Activity activity){
        activity.unbindService(mConnection);
    }

    /**
     * 获取歌曲长度
     **/
    public int getMaxProgress() {
        return mMediaPlayer.getDuration();
    }

    /**
     * 获取播放位置
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 播放指定位置
     */
    public void seekToPosition(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    // 使用后台Service播放音频，界面和后台Service的连接对象，通过此对象进行通信
    private static class AudioServiceConnection implements ServiceConnection{

        public AudioServiceConnection(DelegateBuilder builder) {
            this.builder = builder;
        }

        // 返回控制组件的回调接口，通过该接口获取当前组件，可以进行播放的控制
        private DelegateBuilder builder;
        // 当前控制播放组件
        private AudioDelegate4 delegate;

        // 连接Service时回调，保存控制播放组件
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(builder != null && service instanceof AudioDelegate4){
                delegate = (AudioDelegate4)service;
                // 绑定连接对象是为了方便解绑
                delegate.mConnection = this;
                delegate.recreate();
                builder.onBind(delegate);
            }
        }

        // 断开Service连接时回调，释放资源
        @Override
        public void onServiceDisconnected(ComponentName name) {
            if(delegate != null){
                delegate.release();
            }
        }
    }

    // 返回控制组件的回调接口，通过该接口获取当前组件，可以进行播放的控制
    public interface DelegateBuilder{
        void onBind(AudioDelegate4 delegate);
    }

}