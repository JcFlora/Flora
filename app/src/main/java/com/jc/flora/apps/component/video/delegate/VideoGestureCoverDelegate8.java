package com.jc.flora.apps.component.video.delegate;

import com.jc.flora.apps.component.video.widget.GestureCover8;
import com.jc.flora.apps.component.video.widget.SeekGestureDelegate;

/**
 * Created by Shijincheng on 2019/3/25.
 */

public class VideoGestureCoverDelegate8 {

    private GestureCover8 mGestureCover;
    private VideoDelegate7 mVideoDelegate;

    public void setGestureCover(GestureCover8 gestureCover) {
        mGestureCover = gestureCover;
    }

    public void setVideoDelegate(VideoDelegate7 videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){
            @Override
            public void onSelect(int index, int maxProgress) {
                mGestureCover.setDuration(maxProgress);
            }

            @Override
            public void onProgress(int progress) {
                mGestureCover.setCurrentPosition(progress);
            }
        });
        mGestureCover.setOnSeekGestureListener(new SeekGestureDelegate.OnSeekGestureListener() {
            @Override
            public void onSeekGestureEnd(int progress) {
                mVideoDelegate.seekTo(progress);
            }
        });
    }

}
