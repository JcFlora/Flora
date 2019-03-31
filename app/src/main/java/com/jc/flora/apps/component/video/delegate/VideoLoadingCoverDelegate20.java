package com.jc.flora.apps.component.video.delegate;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;
import com.jc.flora.apps.component.video.widget.SeekGestureDelegate;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2019/3/31.
 */

public class VideoLoadingCoverDelegate20 {

    // mp4列表
    private ArrayList<MP4> mMp4List;

    private View mLoadingCover;
    private ImageView mIvPrepareAlbum;
    private VideoDelegate20 mVideoDelegate;

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

    public void setIvPrepareAlbum(ImageView ivPrepareAlbum) {
        mIvPrepareAlbum = ivPrepareAlbum;
    }

    public void setVideoDelegate(VideoDelegate20 videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public void onPrepareStart(int index) {
                Glide.with(mIvPrepareAlbum).load(mMp4List.get(index).videoAlbum).into(mIvPrepareAlbum);
                mLoadingCover.setVisibility(View.VISIBLE);
                mIvPrepareAlbum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPrepareEnd() {
                mLoadingCover.setVisibility(View.GONE);
                mIvPrepareAlbum.setVisibility(View.GONE);
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
                mLoadingCover.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mLoadingCover.setVisibility(View.GONE);
            }
        });

    }

}