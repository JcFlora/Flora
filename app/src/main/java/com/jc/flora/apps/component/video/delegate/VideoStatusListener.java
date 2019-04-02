package com.jc.flora.apps.component.video.delegate;

/**
 * 视频播放状态监听器
 * Created by Samurai on 2019/3/24.
 */
public class VideoStatusListener {

    /** 视频准备开始时的回调 */
    public void onPrepareStart(int index){}

    /** 视频准备结束时的回调 */
    public void onPrepareEnd(){}

    /** 选择或切换一个视频时的回调 */
    public void onSelect(int index, int maxProgress){}

    /** 播放一个视频时的回调 */
    public void onPlay(){}

    /** 视频缓冲进度变化的回调 */
    public void onBufferingUpdate(int percent){}

    /** 视频缓冲开始时的回调 */
    public void onBufferingStart(){}

    /** 视频缓冲结束时的回调 */
    public void onBufferingEnd(){}

    /** 暂停一个视频时的回调 */
    public void onPause(){}

    /** 停止一个视频时的回调 */
    public void onStop(){}

    /** 完成一个视频时的回调 */
    public void onComplete(){}

    /** 播放进度回调 */
    public void onProgress(int progress){}

//    /** 全屏时的回调 */
//    public void onFullScreen(){}
//
//    /** 半屏时的回调 */
//    public void onHalfScreen(){}

}