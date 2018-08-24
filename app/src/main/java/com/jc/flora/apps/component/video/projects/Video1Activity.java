package com.jc.flora.apps.component.video.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.video.delegate.Video1Delegate;

/**
 * Created by Samurai on 2018/8/23.
 */
public class Video1Activity extends AppCompatActivity {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用VideoView播放本地MP4");
        setContentView(R.layout.activity_video1);
        initLayoutHeight();
        initVideoView();
    }

    private void initLayoutHeight(){
        View vLayoutVideo = findViewById(R.id.layout_video);
        Fidelity.getInstance(this).setHeight(vLayoutVideo, VIDEO_HEIGHT);
    }

    private void initVideoView(){
        VideoView videoView = (VideoView) findViewById(R.id.vv_video);
        new Video1Delegate().setVideoView(videoView).addToActivity(this,"videoDelegate");
    }

}