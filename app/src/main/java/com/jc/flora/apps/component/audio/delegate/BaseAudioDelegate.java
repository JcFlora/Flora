package com.jc.flora.apps.component.audio.delegate;

import android.os.Binder;

import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/10/15.
 */

public class BaseAudioDelegate extends Binder {

    /**
     * 设置播放的mp3列表
     * @param mp3List 播放的mp3列表
     */
    public void setMp3List(ArrayList<MP3> mp3List) {}

    /**
     * 设置播放模式
     * @param audioPlayMode 播放模式
     */
    public void setPlayMode(AudioPlayMode audioPlayMode) {}

    /**
     * 添加音频播放状态监听回调
     * @param l 音频播放状态监听回调
     */
    public void addAudioStatusListener(AudioStatusListener l) {}

    /**
     * 移除音频播放状态监听回调
     * @param l 音频播放状态监听回调
     */
    public void removeAudioStatusListener(AudioStatusListener l) {}

    /**
     * 播放音频（继续播放）
     */
    public void playAudio(){}

    /**
     * 暂停播放
     */
    public void pauseAudio(){}

    /**
     * 复位（从头开始播放）
     */
    public void resetAudio() {}

    /**
     * 切换音频（从头开始播放）
     * 修改为默认列表循环播放模式
     * @param index 切换的音频索引
     */
    public void selectAudio(int index) {}

    /**
     * 播放下一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void nextAudio() {}

    /**
     * 播放上一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void preAudio() {}

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {}

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate() {}


    /**
     * 播放音频
     */
    public void start() {}

    /**
     * 获取当前播放的mp3文件索引
     * @return 当前播放的mp3文件索引
     */
    public int getCurrentMp3Index() {
        return 0;
    }

    /**
     * 获取当前播放的mp3
     * @return 当前播放的mp3
     */
    public MP3 getCurrentMp3() {
        return null;
    }

    /**
     * 获取歌曲长度
     **/
    public int getMaxProgress() {
        return 0;
    }

    /**
     * 获取播放位置
     */
    public int getCurrentPosition() {
        return -1;
    }

    /**
     * 播放指定位置
     */
    public void seekToPosition(int msec) {}

    /**
     * 2.2增加
     * 同步mp3列表
     */
    public void syncMp3List() {}

    /**
     * 2.4增加
     * 快退15秒
     */
    public void rewind(){}

    /**
     * 2.4增加
     * 快进15秒
     */
    public void forward(){}

    /**
     * 2.5增加
     * 倍速播放功能
     * @param audioPlaySpeed
     */
    public void setSpeed(AudioPlaySpeed audioPlaySpeed) {}

    /**
     * 2.8增加
     * 通知被拦截后的回调
     * @param index
     * @param flag
     */
    public void notifyIntercepted(int index, int flag){}

}