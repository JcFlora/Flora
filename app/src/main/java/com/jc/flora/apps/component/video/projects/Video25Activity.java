package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jc.flora.R;
import com.jc.flora.apps.component.video.adapter.VideoAdapter;
import com.jc.flora.apps.component.video.delegate.VideoCompleteCoverDelegate21;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate20;
import com.jc.flora.apps.component.video.delegate.VideoDelegate24;
import com.jc.flora.apps.component.video.delegate.VideoErrorCoverDelegate23;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate17;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate8;
import com.jc.flora.apps.component.video.delegate.VideoListPlayDelegate15;
import com.jc.flora.apps.component.video.delegate.VideoLoadingCoverDelegate20;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.player.PlayerFactory;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/4/4.
 */
public class Video25Activity extends AppCompatActivity {

    // mp4列表
    private static final ArrayList<MP4> MP4_LIST = new ArrayList<MP4>() {
        {
            add(new MP4(
                    "你欠缺的也许并不是能力",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4",
                    "83.1MB"));
            add(new MP4(
                    "坚持与放弃",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/0/4/e4c8836bfe154d76a808da38d0733304.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2015/08/27/SB13F5AGJ_sd.mp4",
                    "77.7MB"));
            add(new MP4(
                    "不想从被子里出来",
                    "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/12/SD70VQJ74_sd.mp4",
                    "16.3MB"));
            add(new MP4(
                    "不耐烦的中国人?",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/9/ac655948c705413b8a63a7aaefd4cde9.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4",
                    "22.8MB"));
            add(new MP4(
                    "神奇的珊瑚",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/4/75bc6c5227314e63bbfd5d9f0c5c28e4.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2016/01/11/SBC46Q9DV_hd.mp4",
                    "59.5MB"));
            add(new MP4(
                    "怎样经营你的人脉",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2018/3/b/c/9d451a2da3cf42b0a049ba3e249222bc.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2018/04/19/SDEQS1GO6_hd.mp4",
                    "55.9MB"));
            add(new MP4(
                    "怎么才能不畏将来",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2018/1/c/8/1aec3637270f465faae52713a7c191c8.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/25/SD82Q0AQE_hd.mp4",
                    "56.6MB"));
            add(new MP4(
                    "音乐和艺术如何改变世界",
                    "http://open-image.nosdn.127.net/image/snapshot_movie/2017/12/2/8/f30dd5f2f09c405c98e7eb6c06c89928.jpg",
                    "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4",
                    "147MB"));
        }
    };

    private Toolbar mToolbar;
    private RecyclerView mRvVideo;
    private View mLayoutVideoRender;
    private ImageView mBtnSwitchScreen;
    private GestureCover10 mGestureCover;

    private VideoDelegate24 mVideoDelegate;
    private VideoControllerDelegate20 mControllerDelegate;
    private VideoListPlayDelegate15 mListPlayDelegate;
    private VideoFullScreenDelegate17 mFullScreenDelegate;
    private VideoGestureCoverDelegate8 mGestureCoverDelegate;
    private VideoLoadingCoverDelegate20 mLoadingCoverDelegate;
    private VideoCompleteCoverDelegate21 mCompleteCoverDelegate;
    private VideoErrorCoverDelegate23 mErrorCoverDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video15);
        findViews();
        initViews();
        initVideoDelegate();
        initControllerDelegate();
        initGestureCoverDelegate();
        initVideoListPlayDelegate();
        initFullScreenDelegate();
        initLoadingDelegate();
        initCompleteDelegate();
        initErrorDelegate();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvVideo = findViewById(R.id.rv_video);
        mRvVideo.setLayoutManager(new LinearLayoutManager(this));
        mLayoutVideoRender = LayoutInflater.from(this).inflate(R.layout.layout_video_render22, mRvVideo, false);
        mBtnSwitchScreen = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_switch_screen);
        mGestureCover = mLayoutVideoRender.findViewById(R.id.layout_gesture_cover);
    }

    private void initViews(){
        mToolbar.setTitle("使用ExoPlayer播放引擎");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    private void initVideoDelegate(){
        TextureView textureView = mLayoutVideoRender.findViewById(R.id.ttv_video);

        mVideoDelegate = new VideoDelegate24();
        mVideoDelegate.setTextureView(textureView);
        mVideoDelegate.setPlayerType(PlayerFactory.PlayerType.EXO);
        mVideoDelegate.setMp4List(MP4_LIST);
        mVideoDelegate.addToActivity(this, "videoDelegate");
    }

    private void initControllerDelegate(){
        View layoutVideo = mLayoutVideoRender.findViewById(R.id.layout_video);
        View layoutController = mLayoutVideoRender.findViewById(R.id.layout_controller);
        ImageView btnPlay = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_play);
        TextView tvCurrentTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_current_time);
        SeekBar sbProgress = (SeekBar) mLayoutVideoRender.findViewById(R.id.sb_progress);
        TextView tvMaxTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_max_time);

        mControllerDelegate = new VideoControllerDelegate20();
        mControllerDelegate.setLayoutVideo(layoutVideo);
        mControllerDelegate.setLayoutController(layoutController);
        mControllerDelegate.setBtnPlay(btnPlay);
        mControllerDelegate.setTvCurrentTime(tvCurrentTime);
        mControllerDelegate.setSbProgress(sbProgress);
        mControllerDelegate.setTvMaxTime(tvMaxTime);
        mControllerDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mControllerDelegate.setGestureCover(mGestureCover);
        mControllerDelegate.setVideoDelegate(mVideoDelegate);
        mControllerDelegate.addToActivity(this,"videoControllerDelegate");
    }

    private void initGestureCoverDelegate(){
        mGestureCover.setGestureEnable(false);

        mGestureCoverDelegate = new VideoGestureCoverDelegate8();
        mGestureCoverDelegate.setGestureCover(mGestureCover);
        mGestureCoverDelegate.setVideoDelegate(mVideoDelegate);
        mGestureCoverDelegate.init();
    }

    private void initVideoListPlayDelegate(){
        mListPlayDelegate = new VideoListPlayDelegate15(mRvVideo);
        mListPlayDelegate.setLayoutVideoRender(mLayoutVideoRender);
        mListPlayDelegate.setVideoDelegate(mVideoDelegate);

        VideoAdapter adapter = new VideoAdapter(MP4_LIST);
        adapter.setRenderAttacher(new VideoAdapter.RenderAttacher() {
            @Override
            public boolean addVideoRender(FrameLayout container, int position) {
                return mListPlayDelegate.addVideoRender(container, position);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mListPlayDelegate.playAudioAtPosition(position);
            }
        });
        mRvVideo.setAdapter(adapter);
        mRvVideo.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                int position = mRvVideo.getChildAdapterPosition(view);
                if (position >= 0 && mListPlayDelegate.isCurrentPlay(position)) {
                    mVideoDelegate.pauseVideo();
                }
            }
        });
    }

    private void initFullScreenDelegate(){
        final FrameLayout layoutFullContainer = findViewById(android.R.id.content);

        mFullScreenDelegate = new VideoFullScreenDelegate17();
        mFullScreenDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mFullScreenDelegate.setOnOrientationChangedListener(new VideoFullScreenDelegate17.OnOrientationChangedListener() {
            @Override
            public void onLandscape() {
                mListPlayDelegate.setCurrentPlayPosition(-1);
                if(mLayoutVideoRender.getParent() != null){
                    ((ViewGroup)mLayoutVideoRender.getParent()).removeView(mLayoutVideoRender);
                }
                layoutFullContainer.addView(mLayoutVideoRender);
                mGestureCover.setGestureEnable(true);
            }
            @Override
            public void onPortrait() {
                layoutFullContainer.removeView(mLayoutVideoRender);
                mListPlayDelegate.setCurrentPlayPosition(mVideoDelegate.getCurrentMp4Index());
                mGestureCover.setGestureEnable(false);
            }
        });
        mFullScreenDelegate.addToActivity(this,"videoFullScreenDelegate");
    }

    private void initLoadingDelegate(){
        View loadingCover = mLayoutVideoRender.findViewById(R.id.layout_loading_cover);
        View prepareCover = mLayoutVideoRender.findViewById(R.id.layout_prepare_cover);
        ImageView ivPrepareAlbum = mLayoutVideoRender.findViewById(R.id.iv_prepare_album);

        mLoadingCoverDelegate = new VideoLoadingCoverDelegate20();
        mLoadingCoverDelegate.setMp4List(MP4_LIST);
        mLoadingCoverDelegate.setPrepareCover(prepareCover);
        mLoadingCoverDelegate.setLoadingCover(loadingCover);
        mLoadingCoverDelegate.setIvPrepareAlbum(ivPrepareAlbum);
        mLoadingCoverDelegate.setVideoDelegate(mVideoDelegate);
        mLoadingCoverDelegate.init();
    }

    private void initCompleteDelegate(){
        View completeCover = mLayoutVideoRender.findViewById(R.id.layout_complete_cover);
        View btnReplay = mLayoutVideoRender.findViewById(R.id.btn_replay);

        mCompleteCoverDelegate = new VideoCompleteCoverDelegate21();
        mCompleteCoverDelegate.setCompleteCover(completeCover);
        mCompleteCoverDelegate.setBtnReplay(btnReplay);
        mCompleteCoverDelegate.setVideoDelegate(mVideoDelegate);
        mCompleteCoverDelegate.init();
    }

    private void initErrorDelegate(){
        View errorCover = mLayoutVideoRender.findViewById(R.id.layout_error_cover);
        TextView tvErrorInfo = mLayoutVideoRender.findViewById(R.id.tv_error_info);
        TextView btnRetry = mLayoutVideoRender.findViewById(R.id.btn_retry);

        mErrorCoverDelegate = new VideoErrorCoverDelegate23();
        mErrorCoverDelegate.setMp4List(MP4_LIST);
        mErrorCoverDelegate.setErrorCover(errorCover);
        mErrorCoverDelegate.setTvErrorInfo(tvErrorInfo);
        mErrorCoverDelegate.setBtnRetry(btnRetry);
        mErrorCoverDelegate.setVideoDelegate(mVideoDelegate);
        mErrorCoverDelegate.init();
    }

    @Override
    public void onBackPressed() {
        if(mFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

}