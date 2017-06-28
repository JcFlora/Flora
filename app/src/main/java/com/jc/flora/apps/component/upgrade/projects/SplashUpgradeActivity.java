package com.jc.flora.apps.component.upgrade.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.splash.delegate.Splash2Delegate;
import com.jc.flora.apps.scene.splash.projects.TestMainActivity;
import com.jc.flora.apps.component.upgrade.renovate.Renovate;

/**
 * 闪频页自动检测升级
 * Created by shijincheng on 2017/3/1.
 */
public class SplashUpgradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDelegate();
    }

    private void initViews(){
        ImageView iv = new ImageView(this);
        setContentView(iv);
        iv.setBackgroundResource(R.drawable.splash_bg);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        iv.startAnimation(animation);
    }

    /** 业务管理初始化 */
    private void initDelegate() {
        Renovate.create(this).setOnPassUpgradeCallback(new Runnable() {
            @Override
            public void run() {
                startSplash();
            }
        }).autoCheck();
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