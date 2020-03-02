package com.jc.flora.apps.component.video.delegate;

import android.view.View;

/**
 * Created by Shijincheng on 2019/4/2.
 */

public class VideoCompleteCoverDelegate21 {

    private View mCompleteCover;
    private View mBtnReplay;
    private BaseVideoDelegate mVideoDelegate;

    public void setCompleteCover(View completeCover) {
        mCompleteCover = completeCover;
    }

    public void setBtnReplay(View btnReplay) {
        mBtnReplay = btnReplay;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mBtnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoDelegate.playVideo();
//                mVideoDelegate.selectVideo(mVideoDelegate.getCurrentMp4Index());
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