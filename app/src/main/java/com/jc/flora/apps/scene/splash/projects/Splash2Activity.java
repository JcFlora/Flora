package com.jc.flora.apps.scene.splash.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.splash.delegate.Splash2Delegate;

/**
 * Created by shijincheng on 2017/1/17.
 */
public class Splash2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        startSplash();
    }

    private void initView(){
        ImageView iv = new ImageView(this);
        setContentView(iv);
        iv.setBackgroundResource(R.drawable.splash_bg);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        iv.startAnimation(animation);
    }

    private void startSplash(){
        new Splash2Delegate().setTargetActivity(TestMainActivity.class).addToActivity(this, "splashDelegate");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 直接返回true，是为了屏蔽back键
        return true;
    }

}
