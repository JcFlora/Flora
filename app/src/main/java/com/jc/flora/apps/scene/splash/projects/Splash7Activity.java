package com.jc.flora.apps.scene.splash.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.splash.delegate.Splash7Delegate;

/**
 * Created by shijincheng on 2017/1/24.
 */
public class Splash7Activity extends AppCompatActivity {

    private VideoView mVvVideo;
    private TextView mTvTickView;
    private View mBtnSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash7);
        findViews();
        startSplash();
    }

    private void findViews(){
        mVvVideo = (VideoView) findViewById(R.id.vv_video);
        mTvTickView = (TextView) findViewById(R.id.tv_tick);
        mBtnSkip = findViewById(R.id.btn_skip);
    }

    private void startSplash(){
        new Splash7Delegate().setTargetActivity(TestMainActivity.class)
                .setVideoView(mVvVideo)
                .setTickView(mTvTickView)
                .setSkipButton(mBtnSkip)
                .addToActivity(this, "splashDelegate");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 直接返回true，是为了屏蔽back键
        return true;
    }

}
