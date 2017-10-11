package com.jc.flora.apps.component.audio.delegate;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.audio.service.Audio6Service;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/10/8.
 */
public class AudioDelegate6 extends Binder {

    // mp3列表
    private ArrayList<MP3> mMp3List;

    // 当前播放的mp3文件索引
    private int mCurrentMp3Index = 0;

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
        Intent intent = new Intent(activity, Audio6Service.class);
        activity.bindService(intent, new AudioServiceConnection(builder), Activity.BIND_AUTO_CREATE);
    }

    public AudioDelegate6(Context ctx) {
        mContext = ctx;
    }

    /**
     * 设置播放的mp3列表
     * @param mp3List 播放的mp3列表
     */
    public void setMp3List(ArrayList<MP3> mp3List) {
        mMp3List = mp3List;
        mCurrentMp3Index = 0;
        recreate();
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
     * 切换音频（从头开始播放）
     * @param index 切换的音频索引
     */
    public void selectAudio(int index) {
        mCurrentMp3Index = index;
        resetAudio();
    }

    /**
     * 播放下一个音频
     */
    public void nextAudio() {
        if(mCurrentMp3Index < mMp3List.size()-1){
            mCurrentMp3Index++;
            resetAudio();
        }
    }

    /**
     * 播放上一个音频
     */
    public void preAudio() {
        if(mCurrentMp3Index > 0){
            mCurrentMp3Index--;
            resetAudio();
        }
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
            mMediaPlayer = MediaPlayer.create(mContext, mMp3List.get(mCurrentMp3Index).resId);
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
     * 获取当前播放的mp3文件索引
     * @return 当前播放的mp3文件索引
     */
    public int getCurrentMp3Index() {
        return mCurrentMp3Index;
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
        private AudioDelegate6 delegate;

        // 连接Service时回调，保存控制播放组件
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(builder != null && service instanceof AudioDelegate6){
                delegate = (AudioDelegate6)service;
                // 绑定连接对象是为了方便解绑
                delegate.mConnection = this;
                // 注意这里去掉了，连接Service后立刻初始化，放到setMp3List()方法里初始化
                // 在Activity中手动调用setMp3List()方法
                //delegate.recreate();
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
        void onBind(AudioDelegate6 delegate);
    }

}