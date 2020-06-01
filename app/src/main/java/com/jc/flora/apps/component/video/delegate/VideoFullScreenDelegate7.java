package com.jc.flora.apps.component.video.delegate;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;

/**
 * 注意，对应Activity需要配置android:configChanges="keyboardHidden|orientation|screenSize"
 * Created by Shijincheng on 2019/3/24.
 */

public class VideoFullScreenDelegate7 extends Fragment {

    /** 视频在720p高保真下的高度，实际开发中，这个值一般通过视频的宽高度比例设置为固定值 */
    private static final double VIDEO_HEIGHT = 720d * 434 / 800;

    // 标题
    private View mVHead;
    // 视频父布局
    private View mLayoutVideo;
    // 全屏/小屏切换
    private ImageView mBtnSwitchScreen;

    public void setHead(View vHead) {
        mVHead = vHead;
    }

    public void setLayoutVideo(View layoutVideo) {
        mLayoutVideo = layoutVideo;
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
        Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
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
            Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mBtnSwitchScreen.setImageResource(R.drawable.video_normal_screen);
            mVHead.setVisibility(View.GONE);
        }else{
            quitFullScreen();
            Fidelity.getInstance(getActivity()).setWidthHeight(mLayoutVideo,
                    ViewGroup.LayoutParams.MATCH_PARENT, VIDEO_HEIGHT);
            mBtnSwitchScreen.setImageResource(R.drawable.video_full_screen);
            mVHead.setVisibility(View.VISIBLE);
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