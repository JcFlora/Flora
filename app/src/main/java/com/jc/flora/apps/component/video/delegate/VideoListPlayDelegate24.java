package com.jc.flora.apps.component.video.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public class VideoListPlayDelegate24 {

    private RecyclerView mRvVideo;
    /** 视频渲染视图 */
    private View mLayoutVideoRender;

    private VideoDelegate24 mVideoDelegate;

    /** 当前播放视频的位置 */
    private int mCurrentPlayPosition = -1;

    public VideoListPlayDelegate24(RecyclerView rv) {
        mRvVideo = rv;
    }

    public void setLayoutVideoRender(View layoutVideoRender) {
        mLayoutVideoRender = layoutVideoRender;
    }

    public void setVideoDelegate(VideoDelegate24 videoDelegate) {
        this.mVideoDelegate = videoDelegate;
    }

    public void setCurrentPlayPosition(int position) {
        if(mCurrentPlayPosition == position){
            return;
        }
        mCurrentPlayPosition = position;
        mRvVideo.getAdapter().notifyDataSetChanged();
    }

    public boolean isCurrentPlay(int position){
        return position == mCurrentPlayPosition;
    }

    public boolean addVideoRender(FrameLayout container, int position){
        if(container.getChildCount() > 2){
            container.removeViewAt(1);
        }
        if(isCurrentPlay(position)){
            if(mLayoutVideoRender.getParent() != null){
                ((ViewGroup)mLayoutVideoRender.getParent()).removeView(mLayoutVideoRender);
            }
            container.addView(mLayoutVideoRender, 1);
            return true;
        }
        return false;
    }

    public void playAudioAtPosition(int position){
        if(isCurrentPlay(position)){
            return;
        }
        boolean isFirstPlay = mLayoutVideoRender.getParent() == null;
        if(isFirstPlay){
            playAudioWhenFirst(position);
        }else{
            playAudioWhenChangePosition(position);
        }
    }

    private void playAudioWhenFirst(final int position){
        setCurrentPlayPosition(position);
        // 必须通过post的方式触发对应位置的视频播放，
        // 如果立刻调用，会因为第一次add的TextureView的onSurfaceTextureAvailable还未调用到，而无法立刻播放
        mRvVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVideoDelegate.selectVideo(position);
            }
        },200);
    }

    private void playAudioWhenChangePosition(final int position){
        setCurrentPlayPosition(-1);
        mVideoDelegate.selectVideo(position);
        // 通过post的方式添加播放器视图，
        // 如果立刻添加，播放器视图会闪现前一个视频的最后一帧
        mRvVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentPlayPosition(position);
            }
        },200);
    }

}