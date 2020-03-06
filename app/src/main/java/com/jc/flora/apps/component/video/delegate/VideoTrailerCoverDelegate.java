package com.jc.flora.apps.component.video.delegate;

import android.view.View;

import com.jc.flora.apps.component.video.model.MP4;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2020/3/5.
 */

public class VideoTrailerCoverDelegate {

    // mp4列表
    private ArrayList<MP4> mMp4List;

    private View mTrailerCover;
    private View mBtnCloseTrailer, mBtnCancel;
    private BaseVideoDelegate mVideoDelegate;

    /**
     * 设置播放的mp4列表
     * @param mp4List 播放的mp4列表
     */
    public void setMp4List(ArrayList<MP4> mp4List) {
        mMp4List = mp4List;
    }

    public void setTrailerCover(View trailerCover) {
        mTrailerCover = trailerCover;
    }

    public void setBtnCloseTrailer(View btnCloseTrailer) {
        mBtnCloseTrailer = btnCloseTrailer;
    }

    public void setBtnCancel(View btnCancel) {
        mBtnCancel = btnCancel;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mBtnCloseTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrailerCover.setVisibility(View.GONE);
                mMp4List.get(mVideoDelegate.getCurrentMp4Index()).trailerPosition = -1;
                mVideoDelegate.playVideo();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrailerCover.setVisibility(View.GONE);
            }
        });
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public void onSelectStart(int index) {
                mTrailerCover.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareStart(int index) {
                mTrailerCover.setVisibility(View.GONE);
            }

            @Override
            public void onSelectEnd(int index, int maxProgress) {
                mTrailerCover.setVisibility(View.GONE);
            }

            @Override
            public void onPlay() {
                mTrailerCover.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mTrailerCover.setVisibility(View.GONE);
            }
        });

    }

}