package com.jc.flora.apps.component.video.delegate;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/27.
 */

public class VideoFullScreenDelegate14 extends Fragment {

    // 视频父布局
    private View mLayoutVideo;
    // 视频容器
    private FrameLayout mLayoutContainer;
    // 全屏视频容器
    private FrameLayout mLayoutFullContainer;
    // 全屏/小屏切换
    private ImageView mBtnSwitchScreen;

    public void setLayoutVideo(View layoutVideo) {
        mLayoutVideo = layoutVideo;
    }

    public void setLayoutContainer(FrameLayout layoutContainer) {
        mLayoutContainer = layoutContainer;
    }

    public void setLayoutFullContainer(FrameLayout layoutFullContainer) {
        mLayoutFullContainer = layoutFullContainer;
    }

    public void setBtnSwitchScreen(ImageView btnSwitchScreen) {
        mBtnSwitchScreen = btnSwitchScreen;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mBtnSwitchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screenOrientation = getActivity().getRequestedOrientation();
                boolean isLandscape = screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
                getActivity().setRequestedOrientation(isLandscape ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            setFullScreen();
            mBtnSwitchScreen.setImageResource(R.drawable.video_normal_screen);
            mLayoutContainer.removeView(mLayoutVideo);
            mLayoutFullContainer.addView(mLayoutVideo);
        }else{
            quitFullScreen();
            mBtnSwitchScreen.setImageResource(R.drawable.video_full_screen);
            mLayoutFullContainer.removeView(mLayoutVideo);
            mLayoutContainer.addView(mLayoutVideo);
        }
    }

    private void setFullScreen(){
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void quitFullScreen(){
        final WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().setAttributes(attrs);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public boolean onBackPressed(){
        int screenOrientation = getActivity().getRequestedOrientation();
        boolean isLandscape = screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        if(isLandscape){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return isLandscape;
    }

}