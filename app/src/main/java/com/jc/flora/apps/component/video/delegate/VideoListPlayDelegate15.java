package com.jc.flora.apps.component.video.delegate;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Shijincheng on 2019/3/30.
 */

public class VideoListPlayDelegate15 {

    private RecyclerView mRvVideo;
    /** 视频渲染视图 */
    private View mLayoutVideoRender;

    private BaseVideoDelegate mVideoDelegate;

    /** 当前播放视频的位置 */
    private int mCurrentPlayPosition = -1;

    public VideoListPlayDelegate15(RecyclerView rv) {
        mRvVideo = rv;
    }

    public void setLayoutVideoRender(View layoutVideoRender) {
        mLayoutVideoRender = layoutVideoRender;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
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
        setCurrentPlayPosition(position);
        mVideoDelegate.selectVideo(position, true);
    }

}