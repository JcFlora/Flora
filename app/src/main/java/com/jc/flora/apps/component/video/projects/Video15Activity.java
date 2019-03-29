package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jc.flora.R;
import com.jc.flora.apps.component.video.adapter.VideoAdapter;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate14;
import com.jc.flora.apps.component.video.delegate.VideoDelegate13;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/28.
 */
public class Video15Activity extends AppCompatActivity {

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
    private VideoAdapter mAdapter;
    private View mLayoutVideoRender;

    private VideoDelegate13 mVideoDelegate;
    private VideoControllerDelegate14 mControllerDelegate;

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
        initVideoList();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvVideo = findViewById(R.id.rv_video);
    }

    private void initViews(){
        mToolbar.setTitle("实现列表播放");
        mToolbar.setTitleTextColor(Color.WHITE);
        mRvVideo.setLayoutManager(new LinearLayoutManager(this));
        mLayoutVideoRender = LayoutInflater.from(this).inflate(R.layout.layout_video_render, mRvVideo, false);
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
        ImageView mBtnPlay = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_play);
        ImageView mBtnSwitchScreen = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_switch_screen);
        TextView mTvCurrentTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_current_time);
        SeekBar mSbProgress = (SeekBar) mLayoutVideoRender.findViewById(R.id.sb_progress);
        TextView mTvMaxTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_max_time);
        GestureCover10 mGestureCover = mLayoutVideoRender.findViewById(R.id.layout_gesture_cover);

        mControllerDelegate = new VideoControllerDelegate14();
        mControllerDelegate.setLayoutVideo(layoutVideo);
        mControllerDelegate.setLayoutController(layoutController);
        mControllerDelegate.setBtnPlay(mBtnPlay);
        mControllerDelegate.setTvCurrentTime(mTvCurrentTime);
        mControllerDelegate.setSbProgress(mSbProgress);
        mControllerDelegate.setTvMaxTime(mTvMaxTime);
        mControllerDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mControllerDelegate.setGestureCover(mGestureCover);
        mControllerDelegate.setVideoDelegate(mVideoDelegate);
        mControllerDelegate.addToActivity(this,"videoControllerDelegate");
    }

    private void initVideoList(){
        mAdapter = new VideoAdapter(MP4_LIST);
        mAdapter.setLayoutVideoRender(mLayoutVideoRender);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                playAudioAtPosition(position);
            }
        });
        mRvVideo.setAdapter(mAdapter);
    }

    private void playAudioAtPosition(int position){
        if(mAdapter.isCurrentPlay(position)){
            return;
        }
        boolean isFirstPlay = mLayoutVideoRender.getParent() == null;
        if(isFirstPlay){
            playAudioWhenFirst(position);
        }else{
            playAudioWhenChangePosition(position);
        }
    }

    private void playAudioWhenFirst(final int position){
        mAdapter.setCurrentPlayPosition(position);
        // 必须通过post的方式触发对应位置的视频播放，
        // 如果立刻调用，会因为第一次add的TextureView的onSurfaceTextureAvailable还未调用到，而无法立刻播放
        mRvVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVideoDelegate.selectVideo(position);
            }
        },200);
    }

    private void playAudioWhenChangePosition(final int position){
        mAdapter.setCurrentPlayPosition(-1);
        mVideoDelegate.selectVideo(position);
        // 通过post的方式添加播放器视图，
        // 如果立刻添加，播放器视图会闪现前一个视频的最后一帧
        mRvVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setCurrentPlayPosition(position);
            }
        },200);
    }

}