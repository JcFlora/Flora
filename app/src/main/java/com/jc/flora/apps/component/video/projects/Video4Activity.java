package com.jc.flora.apps.component.video.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.video.delegate.VideoDelegate4;

/**
 * Created by Samurai on 2018/8/27.
 */
public class Video4Activity extends AppCompatActivity {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自定义进度显示和控制");
        setContentView(R.layout.activity_video4);
        initLayoutHeight();
        initVideoView();
    }

    private void initLayoutHeight(){
        View vLayoutVideo = findViewById(R.id.layout_video);
        Fidelity.getInstance(this).setHeight(vLayoutVideo, VIDEO_HEIGHT);
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