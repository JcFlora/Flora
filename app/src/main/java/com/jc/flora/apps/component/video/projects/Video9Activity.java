package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate8;
import com.jc.flora.apps.component.video.delegate.VideoDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate8;
import com.jc.flora.apps.component.video.widget.GestureCover9;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/25.
 */
public class Video9Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private VideoView mVideoView;
    private View mLayoutVideo, mLayoutController;
    private ImageView mBtnPlay, mBtnSwitchScreen;
    private TextView mTvCurrentTime, mTvMaxTime;
    private SeekBar mSbProgress;
    private GestureCover9 mGestureCover;

    private VideoDelegate7 mVideoDelegate;
    private VideoControllerDelegate8 mControllerDelegate;
    private VideoGestureCoverDelegate8 mGestureCoverDelegate;
    private VideoFullScreenDelegate7 mFullScreenDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video9);
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
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        mLayoutController = findViewById(R.id.layout_controller);
        mBtnPlay = (ImageView) findViewById(R.id.btn_play);
        mBtnSwitchScreen = (ImageView) findViewById(R.id.btn_switch_screen);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        mGestureCover = findViewById(R.id.layout_gesture_cover);
    }

    private void initViews(){
        mToolbar.setTitle("添加亮度控制手势浮层");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    private void initVideoDelegate(){
        mVideoDelegate = new VideoDelegate7();
        mVideoDelegate.setVideoView(mVideoView);
        mVideoDelegate.addToActivity(this, "videoDelegate");
    }

    private void initControllerDelegate(){
        mControllerDelegate = new VideoControllerDelegate8();
        mControllerDelegate.setLayoutVideo(mLayoutVideo);
        mControllerDelegate.setVideoView(mVideoView);
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

    @Override
    public void onBackPressed() {
        if(mFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

}