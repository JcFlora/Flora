package com.jc.flora.apps.component.video.delegate;

import android.view.View;
import android.widget.TextView;

import com.jc.flora.apps.component.deviceinfo.NetworkUtils;
import com.jc.flora.apps.component.video.model.MP4;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public class VideoErrorCoverDelegate23 {

    /** 是否全局忽视移动网络检测 */
    private static final boolean IGNORE_MOBILE_CHECK = false;

    // mp4列表
    private ArrayList<MP4> mMp4List;

    private View mErrorCover;
    private TextView mTvErrorInfo;
    private TextView mBtnRetry;
    private VideoDelegate22 mVideoDelegate;

    private boolean mUserAgreeMobile = false;

    /**
     * 设置播放的mp4列表
     * @param mp4List 播放的mp4列表
     */
    public void setMp4List(ArrayList<MP4> mp4List) {
        mMp4List = mp4List;
    }

    public void setErrorCover(View errorCover) {
        mErrorCover = errorCover;
    }

    public void setTvErrorInfo(TextView tvErrorInfo) {
        mTvErrorInfo = tvErrorInfo;
    }

    public void setBtnRetry(TextView btnRetry) {
        mBtnRetry = btnRetry;
    }

    public void setVideoDelegate(VideoDelegate22 videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mBtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("继续".equals(mBtnRetry.getText())){
                    mUserAgreeMobile = true;
                }
                mVideoDelegate.selectVideo(mVideoDelegate.getCurrentMp4Index());
            }
        });
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public boolean onPlayIntercepted() {
                int networkState = NetworkUtils.getNetworkState(mBtnRetry.getContext());
                if (networkState < 0) {
                    mTvErrorInfo.setText("无网络！");
                    mBtnRetry.setText("重试");
                    mErrorCover.setVisibility(View.VISIBLE);
                    return true;
                } else if (networkState == NetworkUtils.NETWORK_STATE_WIFI) {
                    mErrorCover.setVisibility(View.GONE);
                    return false;
                } else if (IGNORE_MOBILE_CHECK || mUserAgreeMobile) {
                    mErrorCover.setVisibility(View.GONE);
                    return false;
                } else {
                    String videoCapacity = mMp4List.get(mVideoDelegate.getCurrentMp4Index()).videoCapacity;
                    mTvErrorInfo.setText("播放将消耗" + videoCapacity + "流量");
                    mBtnRetry.setText("继续");
                    mErrorCover.setVisibility(View.VISIBLE);
                    return true;
                }
            }

            @Override
            public void onError() {
                mTvErrorInfo.setText("出错了！");
                mBtnRetry.setText("重试");
                mErrorCover.setVisibility(View.VISIBLE);
            }
        });
    }

}