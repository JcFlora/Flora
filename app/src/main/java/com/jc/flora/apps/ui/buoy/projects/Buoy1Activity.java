package com.jc.flora.apps.ui.buoy.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.buoy.delegate.BuoyDelegate1;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/7/4.
 */
public class Buoy1Activity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("跟随手指移动的悬浮窗口");
        setContentView(R.layout.activity_buoy);
        View ivBuoy = findViewById(R.id.iv_buoy);
        BuoyDelegate1.setFollowing(ivBuoy, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Buoy1Activity.this,"你点击了游泳圈");
            }
        });
    }
}
