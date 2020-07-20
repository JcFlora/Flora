package com.jc.flora.apps.component.video.projects;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate14;
import com.jc.flora.apps.component.video.delegate.VideoDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate14;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate8;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/27.
 */
public class Video14Activity extends AppCompatActivity {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    // mp4列表
    private static final ArrayList<MP4> MP4_LIST = new ArrayList<MP4>() {
        {
            add(new MP4("武则天发迹史", R.raw.video_wzt, R.drawable.video_wzt));
            add(new MP4("李白的败家一生", R.raw.video_libai, R.drawable.video_libai));
            add(new MP4("苏轼的人生哲学", R.raw.video_sushi, R.drawable.video_sushi));
            add(new MP4("有彩虹", R.raw.rainbow, R.drawable.video_rainbow));
        }
    };

    private Toolbar mToolbar;
    private TextureView mTtvVideo;
    private View mLayoutVideo, mLayoutController;
    private ImageView mBtnPlay, mBtnSwitchScreen;
    private TextView mTvCurrentTime, mTvMaxTime;
    private SeekBar mSbProgress;
    private GestureCover10 mGestureCover;
    private View mLayoutAlbumCover;
    private ImageView mIvAlbum;
    private TextView mTvVideoName;

    private FrameLayout mLayoutContainer1, mLayoutContainer2;

    private VideoDelegate13 mVideoDelegate;
    private VideoControllerDelegate14 mControllerDelegate;
    private VideoGestureCoverDelegate8 mGestureCoverDelegate;
    private VideoFullScreenDelegate14 mFullScreenDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video14);
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
        mTtvVideo = (TextureView) findViewById(R.id.ttv_video);
        mLayoutController = findViewById(R.id.layout_controller);
        mBtnPlay = (ImageView) findViewById(R.id.btn_play);
        mBtnSwitchScreen = (ImageView) findViewById(R.id.btn_switch_screen);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        mGestureCover = findViewById(R.id.layout_gesture_cover);
        mLayoutAlbumCover = findViewById(R.id.layout_album_cover);
        mIvAlbum = findViewById(R.id.iv_album);
        mTvVideoName = findViewById(R.id.tv_video_name);
        mLayoutContainer1 = findViewById(R.id.layout_container1);
        mLayoutContainer2 = findViewById(R.id.layout_container2);
    }

    private void initViews(){
        mToolbar.setTitle("支持视频容器的动态切换+全半屏切换优化");
        mToolbar.setTitleTextColor(Color.WHITE);
        Fidelity.getInstance(this).setWidthHeight(mLayoutContainer1,
                ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
        Fidelity.getInstance(this).setWidthHeight(mLayoutContainer2,
                ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
        mLayoutAlbumCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
    }

    private void initVideoDelegate(){
        mVideoDelegate = new VideoDelegate13();
        mVideoDelegate.setTextureView(mTtvVideo);
        mVideoDelegate.setMp4List(MP4_LIST);
        mVideoDelegate.addToActivity(this, "videoDelegate");
    }

    private void initControllerDelegate(){
        mControllerDelegate = new VideoControllerDelegate14();
        mControllerDelegate.setLayoutVideo(mLayoutVideo);
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
        mFullScreenDelegate = new VideoFullScreenDelegate14();
        mFullScreenDelegate.setLayoutVideo(mLayoutVideo);
        mFullScreenDelegate.setLayoutContainer(mLayoutContainer1);
        mFullScreenDelegate.setLayoutFullContainer((FrameLayout) findViewById(android.R.id.content));
        mFullScreenDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mFullScreenDelegate.addToActivity(this,"videoFullScreenDelegate");
    }

    private void playVideo(){
        mLayoutAlbumCover.setVisibility(View.GONE);
        mVideoDelegate.selectVideo(0);
        mTtvVideo.setAlpha(1);
    }

    public void selectVideo(View v){
        showMp4ListDialog();
    }

    public void playInTop(View v){
        mLayoutContainer1.removeView(mLayoutVideo);
        mLayoutContainer2.removeView(mLayoutVideo);
        mLayoutContainer1.addView(mLayoutVideo);
        mFullScreenDelegate.setLayoutContainer(mLayoutContainer1);
    }

    public void playInBottom(View v){
        mLayoutContainer1.removeView(mLayoutVideo);
        mLayoutContainer2.removeView(mLayoutVideo);
        mLayoutContainer2.addView(mLayoutVideo);
        mFullScreenDelegate.setLayoutContainer(mLayoutContainer2);
    }

    private void showMp4ListDialog() {
        final String[] ITEMS = {"武则天发迹史", "李白的败家一生", "苏轼的人生哲学", "有彩虹"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择视频");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectVideo(which);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void selectVideo(int index){
        mIvAlbum.setImageResource(MP4_LIST.get(index).coverImgResId);
        mTvVideoName.setText("MV："+MP4_LIST.get(index).name);
        mLayoutAlbumCover.setVisibility(View.GONE);
        mVideoDelegate.selectVideo(index);
        mTtvVideo.setAlpha(1);
    }

    @Override
    public void onBackPressed() {
        if(mFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

}