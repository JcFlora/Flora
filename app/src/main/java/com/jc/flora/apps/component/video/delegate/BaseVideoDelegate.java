package com.jc.flora.apps.component.video.delegate;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.widget.VideoView;

import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.player.PlayerFactory;

import java.util.ArrayList;

public class BaseVideoDelegate extends Fragment {

    /**
     * 0.7~1.1版本适用
     * @param videoView
     */
    public void setVideoView(VideoView videoView) {}

    /**
     * 添加视频播放状态监听回调
     *
     * @param l 视频播放状态监听回调
     */
    public void addVideoStatusListener(VideoStatusListener l) {}

    public void addToActivity(AppCompatActivity activity, String tag) {
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    /**
     * 0.7~1.1版本适用
     */
    public void start() {}

    /**
     * 0.7~1.1版本适用
     */
    public void pause() {}

    public void seekTo(int progress) {}

    /**
     * 1.2版本添加
     * @param textureView
     */
    public void setTextureView(TextureView textureView) {}

    /**
     * 1.2版本添加
     * 播放视频（继续播放）
     */
    public void playVideo() {}

    /**
     * 1.2版本添加
     * 暂停播放
     */
    public void pauseVideo() {}

    /**
     * 1.2版本添加
     * 释放资源，在页面销毁后调用
     */
    public void release() {}

    /**
     * 1.3版本添加
     * 设置播放的mp4列表
     * @param mp4List 播放的mp4列表
     */
    public void setMp4List(ArrayList<MP4> mp4List) {}

    /**
     * 1.3版本添加
     * 切换视频（从头开始播放）
     * @param index 切换的视频索引
     */
    public void selectVideo(int index) {
        selectVideo(index, false);
    }

    /**
     * 1.3版本添加
     * 切换视频（从头开始播放）
     * @param index 切换的视频索引
     * @param needNewSurface 是否需要新的缓冲区
     */
    public void selectVideo(int index, boolean needNewSurface) {}

    /**
     * 1.3版本添加
     * 复位（从头开始播放）
     */
    public void resetVideo() {}

    /**
     * 1.3版本添加
     * @return
     */
    public int getCurrentMp4Index() {
        return 0;
    }

    /**
     * 2.4版本添加
     * @param type
     */
    public void setPlayerType(PlayerFactory.PlayerType type) {}

}