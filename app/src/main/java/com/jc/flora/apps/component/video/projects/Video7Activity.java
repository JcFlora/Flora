package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate7;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/24.
 */
public class Video7Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private VideoDelegate7 mVideoDelegate;
    private VideoControllerDelegate7 mVideoControllerDelegate;
    private VideoFullScreenDelegate7 mVideoFullScreenDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video6);
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mToolbar.setTitle("添加状态监听实现控制反转");
        mToolbar.setTitleTextColor(Color.WHITE);
        initVideoView();
    }

    private void initVideoView(){
        View layoutVideo = findViewById(R.id.layout_video);
        VideoView videoView = (VideoView) findViewById(R.id.vv_video);
        View layoutController = findViewById(R.id.layout_controller);
        ImageView btnPlay = (ImageView) findViewById(R.id.btn_play);
        TextView tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        SeekBar sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        TextView tvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        ImageView btnSwitchScreen = (ImageView) findViewById(R.id.btn_switch_screen);

        mVideoDelegate = new VideoDelegate7();
        mVideoDelegate.setVideoView(videoView);
        mVideoDelegate.addToActivity(this, "videoDelegate");

        mVideoControllerDelegate = new VideoControllerDelegate7();
        mVideoControllerDelegate.setLayoutVideo(layoutVideo);
        mVideoControllerDelegate.setVideoView(videoView);
        mVideoControllerDelegate.setLayoutController(layoutController);
        mVideoControllerDelegate.setBtnPlay(btnPlay);
        mVideoControllerDelegate.setTvCurrentTime(tvCurrentTime);
        mVideoControllerDelegate.setSbProgress(sbProgress);
        mVideoControllerDelegate.setTvMaxTime(tvMaxTime);
        mVideoControllerDelegate.setBtnSwitchScreen(btnSwitchScreen);
        mVideoControllerDelegate.setVideoDelegate(mVideoDelegate);
        mVideoControllerDelegate.addToActivity(this,"videoControllerDelegate");

        mVideoFullScreenDelegate = new VideoFullScreenDelegate7();
        mVideoFullScreenDelegate.setHead(mToolbar);
        mVideoFullScreenDelegate.setLayoutVideo(layoutVideo);
        mVideoFullScreenDelegate.setBtnSwitchScreen(btnSwitchScreen);
        mVideoFullScreenDelegate.addToActivity(this,"videoFullScreenDelegate");
    }

    @Override
    public void onBackPressed() {
        if(mVideoFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }
}