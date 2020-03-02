package com.jc.flora.apps.component.video.delegate;

import android.view.View;
import android.widget.TextView;

import com.jc.flora.apps.component.deviceinfo.NetworkUtils;

/**
 * Created by Shijincheng on 2019/4/2.
 */

public class VideoErrorCoverDelegate22 {

    private View mErrorCover;
    private TextView mTvErrorInfo;
    private View mBtnRetry;
    private BaseVideoDelegate mVideoDelegate;

    public void setErrorCover(View errorCover) {
        mErrorCover = errorCover;
    }

    public void setTvErrorInfo(TextView tvErrorInfo) {
        mTvErrorInfo = tvErrorInfo;
    }

    public void setBtnRetry(View btnRetry) {
        mBtnRetry = btnRetry;
    }

    public void setVideoDelegate(BaseVideoDelegate videoDelegate) {
        mVideoDelegate = videoDelegate;
    }

    public void init(){
        mBtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoDelegate.selectVideo(mVideoDelegate.getCurrentMp4Index());
            }
        });
        mVideoDelegate.addVideoStatusListener(new VideoStatusListener(){

            @Override
            public boolean onPlayIntercepted() {
                int networkState = NetworkUtils.getNetworkState(mBtnRetry.getContext());
                if(networkState < 0){
                    mTvErrorInfo.setText("无网络！");
                    mErrorCover.setVisibility(View.VISIBLE);
                    return true;
                }else{
                    mErrorCover.setVisibility(View.GONE);
                    return false;
                }
            }

            @Override
            public void onError() {
                mTvErrorInfo.setText("出错了！");
                mErrorCover.setVisibility(View.VISIBLE);
            }
        });
    }

}