package com.jc.flora.apps.component.video.delegate;

import android.view.View;
import android.widget.ImageView;

import com.jc.flora.apps.component.video.model.MP4;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2019/4/2.
 */

public class VideoLoadingCoverDelegate22 {

    // mp4列表
    private ArrayList<MP4> mMp4List;

    private View mLoadingCover;
    private View mPrepareCover;
    private ImageView mIvPrepareAlbum;
    private VideoDelegate22 mVideoDelegate;

    /**
     * 设置播放的mp4列表
     * @param mp4List 播放的mp4列表
     */
    public void setMp4List(ArrayList<MP4> mp4List) {
        mMp4List = mp4List;
    }

    public void setLoadingCover(View loadingCover) {
        mLoadingCover = loadingCover;
    }

    public void setPrepareCover(View prepareCover) {
        mPrepareCover = prepareCover;
    }

    public void setIvPrepareAlbum(ImageView ivPrepareAlbum) {
        mIvPrepareAlbum = ivPrepareAlbum;
    }

    public void setVideoDelegate(VideoDelegate22 videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public void onPrepareStart(int index) {
                mMp4List.get(index).loadAlbum(mIvPrepareAlbum);
                mPrepareCover.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPrepareEnd() {
                mPrepareCover.setVisibility(View.GONE);
            }

            @Override
            public void onBufferingStart() {
                mLoadingCover.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBufferingEnd() {
                mLoadingCover.setVisibility(View.GONE);
            }

            @Override
            public void onStop() {
                mPrepareCover.setVisibility(View.GONE);
                mLoadingCover.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mPrepareCover.setVisibility(View.GONE);
                mLoadingCover.setVisibility(View.GONE);
            }
        });

    }

}