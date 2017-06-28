package com.jc.flora.apps.scene.splash.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.splash.delegate.Splash5Delegate;

/**
 * Created by shijincheng on 2017/1/24.
 */
public class Splash5Activity extends AppCompatActivity {

    private TextView mTvTickView;
    private View mBtnSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash5);
        findViews();
        startSplash();
    }

    private void findViews(){
        mTvTickView = (TextView) findViewById(R.id.tv_tick);
        mBtnSkip = findViewById(R.id.btn_skip);
    }

    private void startSplash(){
        new Splash5Delegate().setTargetActivity(TestMainActivity.class)
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
