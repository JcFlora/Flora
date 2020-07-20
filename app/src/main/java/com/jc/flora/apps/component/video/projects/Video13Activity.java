package com.jc.flora.apps.component.video.projects;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.delegate.VideoControllerDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoDelegate13;
import com.jc.flora.apps.component.video.delegate.VideoFullScreenDelegate7;
import com.jc.flora.apps.component.video.delegate.VideoGestureCoverDelegate8;
import com.jc.flora.apps.component.video.model.MP4;
import com.jc.flora.apps.component.video.widget.GestureCover10;

import java.util.ArrayList;

/**
 * 需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Samurai on 2019/3/27.
 */
public class Video13Activity extends AppCompatActivity {

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

    private VideoDelegate13 mVideoDelegate;
    private VideoControllerDelegate13 mControllerDelegate;
    private VideoGestureCoverDelegate8 mGestureCoverDelegate;
    private VideoFullScreenDelegate7 mFullScreenDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video13);
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
    }

    private void initViews(){
        mToolbar.setTitle("支持多个视频切换播放");
        mToolbar.setTitleTextColor(Color.WHITE);
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
        mControllerDelegate = new VideoControllerDelegate13();
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
        mFullScreenDelegate = new VideoFullScreenDelegate7();
        mFullScreenDelegate.setHead(mToolbar);
        mFullScreenDelegate.setLayoutVideo(mLayoutVideo);
        mFullScreenDelegate.setBtnSwitchScreen(mBtnSwitchScreen);
        mFullScreenDelegate.addToActivity(this,"videoFullScreenDelegate");
    }

    private void playVideo(){
        mLayoutAlbumCover.setVisibility(View.GONE);
        mVideoDelegate.selectVideo(0);
    }

    public void selectVideo(View v){
        showMp4ListDialog();
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
    }

    @Override
    public void onBackPressed() {
        if(mFullScreenDelegate.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

}