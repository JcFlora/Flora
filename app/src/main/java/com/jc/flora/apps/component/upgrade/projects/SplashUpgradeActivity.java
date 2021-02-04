package com.jc.flora.apps.component.upgrade.projects;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.component.upgrade.delegate.RenovateDelegate;
import com.jc.flora.apps.scene.splash.delegate.Splash2Delegate;
import com.jc.flora.apps.scene.splash.projects.TestMainActivity;

/**
 * 闪频页自动检测升级
 * Created by shijincheng on 2017/3/1.
 */
public class SplashUpgradeActivity extends AppCompatActivity {

    /** 版本升级器组件 */
    private RenovateDelegate mRenovateDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDelegate();
        start();
    }

    private void initViews() {
        ImageView iv = new ImageView(this);
        setContentView(iv);
        // 演示一个渐显动画
        iv.setBackgroundResource(R.drawable.splash_bg);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        iv.startAnimation(animation);
    }

    /**
     * 业务管理初始化
     */
    private void initDelegate() {
        // 初始化Renovate框架，实际项目中，在Application的onCreate方法中调用
        RenovateDelegate.init();
        // 初始化Renovate
        mRenovateDelegate = new RenovateDelegate(this);
        mRenovateDelegate.initAutoCheck(mOnPassUpgradeCallback);
    }

    /**
     * 如果设置了启动时检测升级，则检测升级，
     * 否则开启闪屏逻辑
     */
    private void start(){
        if (RenovateDelegate.sAutoCheckUpgrade) {
            mRenovateDelegate.start();
        } else {
            startSplash();
        }
    }

    /**
     * 跳过升级后的回调
     */
    private final Runnable mOnPassUpgradeCallback = new Runnable() {
        @Override
        public void run() {
            startSplash();
        }
    };

    /**
     * 开启闪屏逻辑
     */
    private void startSplash() {
        new Splash2Delegate().setTargetActivity(TestMainActivity.class).addToActivity(this, "splashDelegate");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 直接返回true，是为了屏蔽back键
        return true;
    }

}