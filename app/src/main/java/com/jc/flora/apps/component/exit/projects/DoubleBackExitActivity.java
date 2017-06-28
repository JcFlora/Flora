package com.jc.flora.apps.component.exit.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/1/25.
 */
public class DoubleBackExitActivity extends AppCompatActivity {

    /** 确认退出等待时间 */
    private static final long EXIT_WAIT_TIME = 2000;
    /** 第一次按返回的时间点 */
    private long mExitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("再按一次返回键退出");
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - mExitTime) > EXIT_WAIT_TIME){
            ToastDelegate.show(this, "再按一次返回键退出");
            mExitTime = System.currentTimeMillis();
        } else {
            ToastDelegate.cancel();
            ToastDelegate.onAppExit();
            finish();
        }
    }

}
