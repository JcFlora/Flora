package com.jc.flora.apps.scene.splash.projects;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/1/17.
 */
public class Splash1Activity extends AppCompatActivity{

    // 最小显示时间
    private static final int SHOW_TIME_MIN = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = new ImageView(this);
        setContentView(iv);
        iv.setBackgroundResource(R.drawable.splash_bg);
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mHandler.postDelayed(mGotoMainTask, SHOW_TIME_MIN);
                    break;
                default:
                    break;
            }
        }
    };

    //进入下一个Activity
    Runnable mGotoMainTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(Splash1Activity.this,
                    TestMainActivity.class));
            finish();
        }
    };

}
