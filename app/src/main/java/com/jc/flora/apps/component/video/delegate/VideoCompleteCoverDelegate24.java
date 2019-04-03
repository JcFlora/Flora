package com.jc.flora.apps.component.video.delegate;

import android.view.View;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public class VideoCompleteCoverDelegate24 {

    private View mCompleteCover;
    private View mBtnReplay;
    private VideoDelegate24 mVideoDelegate;

    public void setCompleteCover(View completeCover) {
        mCompleteCover = completeCover;
    }

    public void setBtnReplay(View btnReplay) {
        mBtnReplay = btnReplay;
    }

    public void setVideoDelegate(VideoDelegate24 videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mBtnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoDelegate.playVideo();
            }
        });
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public void onPrepareStart(int index) {
                mCompleteCover.setVisibility(View.GONE);
            }

            @Override
            public void onSelect(int index, int maxProgress) {
                mCompleteCover.setVisibility(View.GONE);
            }

            @Override
            public void onPlay() {
                mCompleteCover.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mCompleteCover.setVisibility(View.VISIBLE);
            }
        });
    }

}