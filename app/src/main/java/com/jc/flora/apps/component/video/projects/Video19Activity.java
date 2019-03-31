package com.jc.flora.apps.component.video.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jc.flora.R;
import com.jc.flora.apps.component.video.adapter.VideoAdapter;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate14;
import com.jc.flora.apps.component.video.delegate.VideoDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate17;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoListPlayDelegate15;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/31.
 */
public class Video19Activity extends AppCompatActivity {

    // mp4列表
    private static final ArrayList<MP4> MP4_LIST = new ArrayList<MP4>() {
        {
            add(new MP4("中国古代的军事实力", "https://m.yunpub.cn/7b99173f1eddc48370dd006edb15c174/5ca1625e/m/201903/21/0ba3f9e4ff6b4b699cfaa2329a59400dm128.mp4", "https://upload.yunpub.cn/i/201903/21/baf25bc80e4c4e00b6ac139cdf4e9515i321.jpeg"));
            add(new MP4("宋朝皇帝的小爱好", "https://m.yunpub.cn/73835faf870a72d18ecfaf0fde39da15/5ca16502/m/201903/15/02ed29db3210403fb794083f61ec90dbm757.mp4", "https://upload.yunpub.cn/i/201903/15/6d71ba47aa89427098f147546721ef68i846.jpg"));
            add(new MP4("玛丽莲·梦露的人间往事", "https://m.yunpub.cn/08ca7ff7e0dc452985ed571efff7261d/5ca16502/m/201901/30/7d07e49886514fc79adb28899295bfb8m658.mp4", "https://upload.yunpub.cn/i/201901/30/3dbb98b085a74f52968635694bfa132ci852.png"));
            add(new MP4("爱因斯坦的传奇一生", "https://m.yunpub.cn/c3989860a9abc7f73228a97b0d37673c/5ca1e050/m/201811/15/38158e1fc146465a8fb0f7bd3f821afbm100.mp4", "https://upload.yunpub.cn/i/201811/15/424eb5f3241e40a4b5efd65a068c86a3i397.jpg"));
            add(new MP4("杜甫的官二代生涯", "https://m.yunpub.cn/59dbad38637fd24031ca569a77dc9ad3/5ca1e307/m/201812/28/74b4c7ba20224170800d6c08659227cem664.mp4", "https://upload.yunpub.cn/i/201812/28/c58b8824fc7f4a6f8071eb581c5123b9i668.jpg"));
            add(new MP4("大秦帝国", "https://m.yunpub.cn/bf7c4b2fad9dcd05ec2a2e2bdf1e8a4e/5ca1e2a7/m/201807/19/f4408565137e4897be469f768ac3daa7m853.mp4", "https://upload.yunpub.cn/i/201807/19/323bb6cf0cd249d99e731738479e1f4bi421.jpg"));
            add(new MP4("科学的真相", "https://m.yunpub.cn/4c380c6e11397ef31e9842dbb1786bad/5ca1e3a3/m/201806/28/fc94844b8c6a4e4c896c996f60be07d6m269.mp4", "https://upload.yunpub.cn/i/201806/28/adc0244c153645e1960c98d6aeb94813i482.jpg"));
            add(new MP4("科学的诞生", "https://m.yunpub.cn/61b7cf74d96510618d9281588c6b4a89/5ca1e3d5/group1/M00/02/05/CjN5vlsDo1SAebZUAslBxG6Ui3k374.mp4", "https://upload.yunpub.cn/group1/M00/02/05/CjN5vlsDosKAbXrwAAf4s6a1bYY842.jpg"));
            add(new MP4("小家越住越大", "https://m.yunpub.cn/19de3ca97d44fddc91a7a290d09f2038/5ca1ebeb/group1/M00/02/0C/CjN5vlsPY6GABWufAjVAilHk_KM439.mp4", "https://upload.yunpub.cn/group1/M00/01/F5/CiyniFsPYymAUKc4AAiIpgL1lC0825.jpg"));
            add(new MP4("一战简史", "https://m.yunpub.cn/2f13670ed64962c7108704e583846e90/5ca1ee9a/group1/M00/01/6A/CjN5vlpAvzSAJT0qAXmXR787wy8851.mp4", "https://upload.yunpub.cn/group1/M00/01/6A/CjN5vlpAvwmATRCLAAXzwVNZu28802.jpg"));
            add(new MP4("两千年政商关系史", "https://m.yunpub.cn/797b2be8773565ec02969a6a4631092b/5ca1ecff/group1/M00/01/4A/CjN5vlofZgOAZYCwAmJfe3TlAhw280.mp4", "https://upload.yunpub.cn/group1/M00/01/43/Cqs08lofZW-ASt53AAZAvNvya60458.jpg"));
            add(new MP4("中央银行养成记", "https://m.yunpub.cn/2e8fe75c9bc2c60fce62441d2eab6c00/5ca1efba/group1/M00/01/3B/CjN5vloDtfGALoU-ArxKakMh85M684.mp4", "https://upload.yunpub.cn/group1/M00/01/30/Cqs08loDtb-ACzI7AAPb3OGxL5I11.jpeg"));
            add(new MP4("新编中国史", "https://m.yunpub.cn/91ef1552d78528deb2d35b5b64be4fdc/5ca20513/group1/M00/01/2D/CiyniFn5JtqAawN_A5PQEAcgGFA660.mp4", "https://upload.yunpub.cn/group1/M00/01/2D/CiyniFn5Jp6AJr37AAWWJp4Xw3c557.jpg"));
            add(new MP4("二战简史", "https://m.yunpub.cn/e3999024d893dab7cc0af6ce44e9d3cd/5ca20578/group1/M00/01/29/Cqs08lnxQUSAHgsQAp1BPG3d3IA825.mp4", "https://upload.yunpub.cn/group1/M00/01/35/CjN5vlnxQESAKj2aAAX6ddLGkqI558.jpg"));
            add(new MP4("白银帝国", "https://m.yunpub.cn/e685b2bb9f77a83e0d59030eaffdaa2f/5ca205d9/group1/M00/00/B2/Cqs08llI5wOANsfdAb1dHLk4y1k186.mp4", "https://upload.yunpub.cn/group1/M00/00/B9/CiyniFlI5huAMdj4AA9OjfRD6no723.png"));
        }
    };

    private Toolbar mToolbar;
    private RecyclerView mRvVideo;
    private View mLayoutVideoRender;
    private ImageView mBtnSwitchScreen;
    private GestureCover10 mGestureCover;

    private VideoDelegate13 mVideoDelegate;
    private VideoControllerDelegate14 mControllerDelegate;
    private VideoListPlayDelegate15 mListPlayDelegate;
    private VideoFullScreenDelegate17 mFullScreenDelegate;
    private VideoGestureCoverDelegate13 mGestureCoverDelegate;

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
        initGestureCoverDelegate();
        initVideoListPlayDelegate();
        initFullScreenDelegate();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvVideo = findViewById(R.id.rv_video);
        mRvVideo.setLayoutManager(new LinearLayoutManager(this));
        mLayoutVideoRender = LayoutInflater.from(this).inflate(R.layout.layout_video_render15, mRvVideo, false);
        mBtnSwitchScreen = (ImageView) mLayoutVideoRender.findViewById(R.id.btn_switch_screen);
        mGestureCover = mLayoutVideoRender.findViewById(R.id.layout_gesture_cover);
    }

    private void initViews(){
        mToolbar.setTitle("播放在线视频");
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
        TextView tvCurrentTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_current_time);
        SeekBar sbProgress = (SeekBar) mLayoutVideoRender.findViewById(R.id.sb_progress);
        TextView tvMaxTime = (TextView) mLayoutVideoRender.findViewById(R.id.tv_max_time);

        mControllerDelegate = new VideoControllerDelegate14();
        mControllerDelegate.setLayoutVideo(layoutVideo);
        mControllerDelegate.setLayoutController(layoutController);
        mControllerDelegate.setBtnPlay(btnPlay);
        mControllerDelegate.setTvCurrentTime(tvCurrentTime);
        mControllerDelegate.setSbProgress(sbProgress);
        mControllerDelegate.setTvMaxTime(tvMaxTime);
        mControllerDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mControllerDelegate.setGestureCover(mGestureCover);
        mControllerDelegate.setVideoDelegate(mVideoDelegate);
        mControllerDelegate.addToActivity(this,"videoControllerDelegate");
    }

    private void initGestureCoverDelegate(){
        mGestureCover.setGestureEnable(false);

        mGestureCoverDelegate = new VideoGestureCoverDelegate13();
        mGestureCoverDelegate.setGestureCover(mGestureCover);
        mGestureCoverDelegate.setVideoDelegate(mVideoDelegate);
        mGestureCoverDelegate.init();
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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

    private void initFullScreenDelegate(){
        final FrameLayout layoutFullContainer = findViewById(android.R.id.content);

        mFullScreenDelegate = new VideoFullScreenDelegate17();
        mFullScreenDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mFullScreenDelegate.setOnOrientationChangedListener(new VideoFullScreenDelegate17.OnOrientationChangedListener() {
            @Override
            public void onLandscape() {
                mListPlayDelegate.setCurrentPlayPosition(-1);
                if(mLayoutVideoRender.getParent() != null){
                    ((ViewGroup)mLayoutVideoRender.getParent()).removeView(mLayoutVideoRender);
                }
                layoutFullContainer.addView(mLayoutVideoRender);
                mGestureCover.setGestureEnable(true);
            }
            @Override
            public void onPortrait() {
                layoutFullContainer.removeView(mLayoutVideoRender);
                mListPlayDelegate.setCurrentPlayPosition(mVideoDelegate.getCurrentMp4Index());
                mGestureCover.setGestureEnable(false);
            }
        });
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