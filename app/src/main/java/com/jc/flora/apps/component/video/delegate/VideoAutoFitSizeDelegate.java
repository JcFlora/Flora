package com.jc.flora.apps.component.video.delegate;

import com.jc.flora.apps.component.video.widget.VideoTextureView;

/**
 * Created by Shijincheng on 2020/7/2.
 */
public class VideoAutoFitSizeDelegate {

    private VideoTextureView mTextureView;

    private BaseVideoDelegate mVideoDelegate;

    public void setTextureView(VideoTextureView textureView) {
        mTextureView = textureView;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){
            @Override
            public void onVideoSizeChanged(int width, int height) {
                super.onVideoSizeChanged(width, height);
                mTextureView.setVideoSize(width, height);
            }
        });
    }

}