package com.jc.flora.apps.ui.buoy.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.buoy.delegate.BuoyDelegate2;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/7/4.
 */
public class Buoy2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加吸附动画");
        setContentView(R.layout.activity_buoy);
        View ivBuoy = findViewById(R.id.iv_buoy);
        BuoyDelegate2.setFollowing(ivBuoy, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Buoy2Activity.this,"你点击了游泳圈");
            }
        });
    }
}
