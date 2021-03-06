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
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jc.flora.R;
import com.jc.flora.apps.component.video.adapter.VideoAdapter;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate14;
import com.jc.flora.apps.component.video.delegate.VideoDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoListPlayDelegate15;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/28.
 */
public class Video16Activity extends AppCompatActivity {

    // mp4列表
    private static final ArrayList<MP4> MP4_LIST = new ArrayList<MP4>() {
        {
            add(new MP4("武则天发迹史", R.raw.video_wzt, R.drawable.video_wzt));
            add(new MP4("李白的败家一生", R.raw.video_libai, R.drawable.video_libai));
            add(new MP4("苏轼的人生哲学", R.raw.video_sushi, R.drawable.video_sushi));
            add(new MP4("有彩虹", R.raw.rainbow, R.drawable.video_rainbow));
            add(new MP4("武则天发迹史2", R.raw.video_wzt, R.drawable.video_wzt));
            add(new MP4("李白的败家一生2", R.raw.video_libai, R.drawable.video_libai));
            add(new MP4("苏轼的人生哲学2", R.raw.video_sushi, R.drawable.video_sushi));
            add(new MP4("有彩虹2", R.raw.rainbow, R.drawable.video_rainbow));
        }
    };

    private Toolbar mToolbar;
    private RecyclerView mRvVideo;
    private View mLayoutVideoRender;

    private VideoDelegate13 mVideoDelegate;
    private VideoControllerDelegate14 mControllerDelegate;
    private VideoListPlayDelegate15 mListPlayDelegate;

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
        initVideoListPlayDelegate();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvVideo = findViewById(R.id.rv_video);
        mRvVideo.setLayoutManager(new LinearLayoutManager(this));
        mLayoutVideoRender = LayoutInflater.from(this).inflate(R.layout.layout_video_render15, mRvVideo, false);
    }

    private void initViews(){
        mToolbar.setTitle("实现滑动到屏幕外自动暂停");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    private void initVideoDelegate(){
        TextureView textureView = mLayoutVideoRender.findViewById(R.id.ttv_video);

        mVideoDelegate = new VideoDelegate13();
        mVideoDelegate.setTextureView(textureView);
        mVideoDelegate.setMp4List(MP4_LIST);
        mVideoDelegate.addToActivity(this, "videoDelegate");
    }

    private void initControllerDelegate(){
        View layoutVideo = mLayoutVideoRender.findViewById(R.id.layout_video);
        View layoutController = mLayoutVideoRender.findViewById(R.id.layout_controller);
        ImageView btnPlay = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_play);
        ImageView btnSwitchScreen = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_switch_screen);
        TextView tvCurrentTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_current_time);
        SeekBar sbProgress = (SeekBar) mLayoutVideoRender.findViewById(R.id.sb_progress);
        TextView tvMaxTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_max_time);
        GestureCover10 gestureCover = mLayoutVideoRender.findViewById(R.id.layout_gesture_cover);

        mControllerDelegate = new VideoControllerDelegate14();
        mControllerDelegate.setLayoutVideo(layoutVideo);
        mControllerDelegate.setLayoutController(layoutController);
        mControllerDelegate.setBtnPlay(btnPlay);
        mControllerDelegate.setTvCurrentTime(tvCurrentTime);
        mControllerDelegate.setSbProgress(sbProgress);
        mControllerDelegate.setTvMaxTime(tvMaxTime);
        mControllerDelegate.setBtnSwitchScreen(btnSwitchScreen);
        mControllerDelegate.setGestureCover(gestureCover);
        mControllerDelegate.setVideoDelegate(mVideoDelegate);
        mControllerDelegate.addToActivity(this,"videoControllerDelegate");
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

}