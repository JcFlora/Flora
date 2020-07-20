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
import com.jc.flora.apps.component.video.delegate.VideoDelegate6;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2018/8/28.
 */
public class Video6Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private VideoDelegate6 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video6);
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mToolbar.setTitle("单个视频的全半屏切换");
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
        mDelegate = new VideoDelegate6();
        mDelegate.setHead(mToolbar);
        mDelegate.setLayoutVideo(layoutVideo);
        mDelegate.setVideoView(videoView);
        mDelegate.setLayoutController(layoutController);
        mDelegate.setBtnPlay(btnPlay);
        mDelegate.setTvCurrentTime(tvCurrentTime);
        mDelegate.setSbProgress(sbProgress);
        mDelegate.setTvMaxTime(tvMaxTime);
        mDelegate.setBtnSwitchScreen(btnSwitchScreen);
        mDelegate.addToActivity(this,"videoDelegate");
    }

    @Override
    public void onBackPressed() {
        if(mDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }
}