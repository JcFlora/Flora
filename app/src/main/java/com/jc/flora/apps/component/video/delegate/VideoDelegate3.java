package com.jc.flora.apps.component.video.delegate;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.jc.flora.R;

import java.util.HashMap;

/**
 * Created by Shijincheng on 2018/8/26.
 */

public class VideoDelegate3 extends Fragment {

    // 视频父布局
    private View mLayoutVideo;
    // 视频视图
    private VideoView mVideoView;
    // 控制层布局
    private View mLayoutController;
    // 播放按钮
    private ImageView mBtnPlay;

    private int mVideoPosition = 0;
    /** 当前视频正在播放 */
    private boolean mIsVideoPlaying = false;
    /** 当前界面正处在前台运行 */
    private boolean mIsInForeground = true;

    public void setLayoutVideo(View layoutVideo) {
        mLayoutVideo = layoutVideo;
    }

    public void setVideoView(VideoView videoView) {
        mVideoView = videoView;
    }

    public void setLayoutController(View layoutController) {
        mLayoutController = layoutController;
    }

    public void setBtnPlay(ImageView btnPlay) {
        mBtnPlay = btnPlay;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsInForeground && mVideoView != null) {
            mIsInForeground = true;
            mVideoView.seekTo(mVideoPosition);
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
            mIsVideoPlaying = mVideoView.isPlaying();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mIsInForeground = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLayoutVideo.removeCallbacks(mFadeOut);
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void initView() {
        mLayoutController.setVisibility(View.GONE);
        mLayoutVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mLayoutController.getVisibility() == View.VISIBLE) {
                        hideController();
                    }else{
                        showController();
                    }
                }
                return false;
            }
        });
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mBtnPlay.setImageResource(R.drawable.video_play);
                } else {
                    mVideoView.start();
                    mBtnPlay.setImageResource(R.drawable.video_pause);
                }
                showController();
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setRemoveBgWhenFirstPlayListener(mp);
                if(mIsInForeground && mIsVideoPlaying){
                    mp.start();
                }
            }
        });
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.rainbow);
    }

    private void hideController(){
        mLayoutController.setVisibility(View.GONE);
    }

    private void showController(){
        mLayoutController.setVisibility(View.VISIBLE);
        mLayoutVideo.removeCallbacks(mFadeOut);
        mLayoutVideo.postDelayed(mFadeOut, 3000);
    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hideController();
        }
    };

    /**
     * 设置第一帧画面为背景，仅适用于网络视频，不适用于本地
     * @param uri 网络视频地址
     */
    private void setFirstFrameForBackground(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri, new HashMap<String, String>());
        Bitmap bitmap = retriever.getFrameAtTime();
        mVideoView.setBackground(new BitmapDrawable(bitmap));
        retriever.release();
    }

    /**
     * 第一次播放时，移除VideoView的封面图
     * @param mp
     */
    private void setRemoveBgWhenFirstPlayListener(MediaPlayer mp){
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    mVideoView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mVideoView.setBackground(null);
                        }
                    },400);
                }
                return true;
            }
        });
    }

}