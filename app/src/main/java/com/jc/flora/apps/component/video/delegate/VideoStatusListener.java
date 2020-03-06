package com.jc.flora.apps.component.video.delegate;

/**
 * 视频播放状态监听器
 * Created by Samurai on 2019/3/24.
 */
public class VideoStatusListener {

    public void onSelectStart(int index){}

    /** 视频准备开始时的回调 */
    public void onPrepareStart(int index){}

    /** 选择或切换一个视频时的回调 */
    public void onSelectEnd(int index, int maxProgress){}

    /** 视频准备结束时的回调 */
    public void onPrepareEnd(){}

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

    /**
     * 在播放前进行拦截，返回true表示拦截，false表示不拦截
     * @return 是否拦截，默认不拦截
     */
    public boolean onPlayIntercepted(){
        return false;
    }

    /** 播放出错时的回调 */
    public void onError(){}

}