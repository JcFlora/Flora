package com.jc.flora.apps.component.video.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.delegate.VideoDelegate4;

/**
 * Created by Samurai on 2018/8/28.
 */
public class Video5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video5);
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
        VideoDelegate4 delegate = new VideoDelegate4();
        delegate.setLayoutVideo(layoutVideo);
        delegate.setVideoView(videoView);
        delegate.setLayoutController(layoutController);
        delegate.setBtnPlay(btnPlay);
        delegate.setTvCurrentTime(tvCurrentTime);
        delegate.setSbProgress(sbProgress);
        delegate.setTvMaxTime(tvMaxTime);
        delegate.addToActivity(this,"videoDelegate");
    }

}