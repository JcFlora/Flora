package com.jc.flora.apps.component.video.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.video.delegate.VideoDelegate2;

/**
 * Created by Samurai on 2018/8/26.
 */
public class Video2Activity extends AppCompatActivity {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自定义播放暂停按钮");
        setContentView(R.layout.activity_video2);
        initLayoutHeight();
        initVideoView();
    }

    private void initLayoutHeight(){
        View vLayoutVideo = findViewById(R.id.layout_video);
        Fidelity.getInstance(this).setHeight(vLayoutVideo, VIDEO_HEIGHT);
    }

    private void initVideoView(){
        VideoView videoView = (VideoView) findViewById(R.id.vv_video);
        ImageView btnPlay = (ImageView) findViewById(R.id.btn_play);
        VideoDelegate2 delegate = new VideoDelegate2();
        delegate.setVideoView(videoView);
        delegate.setBtnPlay(btnPlay);
        delegate.addToActivity(this,"videoDelegate");
    }

}