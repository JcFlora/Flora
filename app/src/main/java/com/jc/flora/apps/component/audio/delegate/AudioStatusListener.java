package com.jc.flora.apps.component.audio.delegate;

/**
 * 音频播放状态监听器
 * Created by Samurai on 2017/10/9.
 */
public class AudioStatusListener {

    /** 切换播放模式时的回调 */
    public void onModeSelect(int index){}

    /** 选择或切换一个音频时的回调 */
    public void onSelect(int index, int maxProgress){}

    /** 继续播放一个音频时的回调 */
    public void onPlay(){}

    /** 暂停一个音频时的回调 */
    public void onPause(){}

    /** 播放进度回调 */
    public void onProgress(int progress){}

}