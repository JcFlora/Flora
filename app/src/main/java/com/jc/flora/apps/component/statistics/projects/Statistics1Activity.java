package com.jc.flora.apps.component.statistics.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import ly.count.android.sdk.Countly;

/**
 * implementation 'ly.count.android:sdk:16.12.2'
 * Created by shijincheng on 2017/1/20.
 */
public class Statistics1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CountlyCloud基础统计");
        ImageView iv = new ImageView(this);
        setContentView(iv);
        Countly.sharedInstance().init(this, "http://157.0.31.8:5666", "31208c2cf3ced4eee7a3d5652f5b2439a2444626");
        Countly.sharedInstance().setLoggingEnabled(true);
        Countly.sharedInstance().setViewTracking(true);
        // 设置上报崩溃
        Countly.sharedInstance().enableCrashReporting();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Countly.sharedInstance().recordEvent("登录");
            }
        });
    }

    @Override
    protected void onStart() {
//        Countly.sharedInstance().recordView("CountlyCloud统计测试");
        Countly.sharedInstance().onStart(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Countly.sharedInstance().onStop();
        super.onStop();
    }

}