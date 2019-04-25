package com.jc.flora.apps.component.audio.delegate;

import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;

/**
 * 音频播放状态监听器
 * Created by Samurai on 2017/10/9.
 */
public class AudioStatusListener {

    /** 切换播放模式时的回调 */
    public void onModeSelect(int index){}

    /** 切换播放速度时的回调 */
    public void onSpeedSelect(int index){}

    /** 选择或切换一个音频时的回调 */
    public void onSelect(int index, int maxProgress){}

    /** 播放一个音频时的回调 */
    public void onPlay(){}

    /** 暂停一个音频时的回调 */
    public void onPause(){}

    /** 停止一个音频时的回调 */
    public void onStop(){}

    /** 播放进度回调 */
    public void onProgress(int progress){}

    /**
     * 在播放前进行拦截，返回true表示拦截，false表示不拦截
     * @return 是否拦截，默认不拦截
     */
    public boolean onPlayIntercepted(){
        return false;
    }

    /** 音频准备开始时的回调 */
    public void onPrepareStart(int index){}

    /** 音频准备结束时的回调 */
    public void onPrepareEnd(){}

    /** 音频缓冲进度变化的回调 */
    public void onBufferingUpdate(int percent){}

    /** 音频缓冲开始时的回调 */
    public void onBufferingStart(){}

    /** 音频缓冲结束时的回调 */
    public void onBufferingEnd(){}

    /**
     * 在选择前进行拦截，返回true表示拦截，false表示不拦截
     * @param mp3List
     * @param index
     * @return 是否拦截，默认不拦截
     */
    public boolean interceptSelect(ArrayList<MP3> mp3List, int index){
        return false;
    }

    /**
     * 被拦截后的回调
     * @param mp3List
     * @param index
     * @param flag
     */
    public void onIntercepted(ArrayList<MP3> mp3List, int index, int flag){}

    /** 播放出错时的回调 */
    public void onError(){}

}