package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate12;
import com.jc.flora.apps.component.video.delegate.VideoDelegate12;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate8;
import com.jc.flora.apps.component.video.widget.GestureCover10;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/26.
 */
public class Video12Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextureView mTtvVideo;
    private View mLayoutVideo, mLayoutController;
    private ImageView mBtnPlay, mBtnSwitchScreen;
    private TextView mTvCurrentTime, mTvMaxTime;
    private SeekBar mSbProgress;
    private GestureCover10 mGestureCover;
    private View mLayoutAlbumCover;

    private VideoDelegate12 mVideoDelegate;
    private VideoControllerDelegate12 mControllerDelegate;
    private VideoGestureCoverDelegate8 mGestureCoverDelegate;
    private VideoFullScreenDelegate7 mFullScreenDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video12);
        findViews();
        initViews();
        initVideoDelegate();
        initControllerDelegate();
        initGestureCoverDelegate();
        initFullScreenDelegate();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mLayoutVideo = findViewById(R.id.layout_video);
        mTtvVideo = (TextureView) findViewById(R.id.ttv_video);
        mLayoutController = findViewById(R.id.layout_controller);
        mBtnPlay = (ImageView) findViewById(R.id.btn_play);
        mBtnSwitchScreen = (ImageView) findViewById(R.id.btn_switch_screen);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        mGestureCover = findViewById(R.id.layout_gesture_cover);
        mLayoutAlbumCover = findViewById(R.id.layout_album_cover);
    }

    private void initViews(){
        mToolbar.setTitle("使用MediaPlayer+TextureView播放");
        mToolbar.setTitleTextColor(Color.WHITE);
        mLayoutAlbumCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
    }

    private void initVideoDelegate(){
        mVideoDelegate = new VideoDelegate12();
        mVideoDelegate.setTextureView(mTtvVideo);
        mVideoDelegate.addToActivity(this, "videoDelegate");
    }

    private void initControllerDelegate(){
        mControllerDelegate = new VideoControllerDelegate12();
        mControllerDelegate.setLayoutVideo(mLayoutVideo);
        mControllerDelegate.setLayoutController(mLayoutController);
        mControllerDelegate.setBtnPlay(mBtnPlay);
        mControllerDelegate.setTvCurrentTime(mTvCurrentTime);
        mControllerDelegate.setSbProgress(mSbProgress);
        mControllerDelegate.setTvMaxTime(mTvMaxTime);
        mControllerDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mControllerDelegate.setGestureCover(mGestureCover);
        mControllerDelegate.setVideoDelegate(mVideoDelegate);
        mControllerDelegate.addToActivity(this,"videoControllerDelegate");
    }

    private void initGestureCoverDelegate(){
        mGestureCoverDelegate = new VideoGestureCoverDelegate8();
        mGestureCoverDelegate.setGestureCover(mGestureCover);
        mGestureCoverDelegate.setVideoDelegate(mVideoDelegate);
        mGestureCoverDelegate.init();
    }

    private void initFullScreenDelegate(){
        mFullScreenDelegate = new VideoFullScreenDelegate7();
        mFullScreenDelegate.setHead(mToolbar);
        mFullScreenDelegate.setLayoutVideo(mLayoutVideo);
        mFullScreenDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mFullScreenDelegate.addToActivity(this,"videoFullScreenDelegate");
    }

    private void playVideo(){
        mLayoutAlbumCover.setVisibility(View.GONE);
        mVideoDelegate.playVideo();
    }

    @Override
    public void onBackPressed() {
        if(mFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

}