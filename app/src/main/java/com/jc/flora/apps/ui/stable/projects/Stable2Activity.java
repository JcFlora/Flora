package com.jc.flora.apps.ui.stable.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StableDelegate(this).setStatusBarColorResource(android.R.color.holo_blue_light);
        setTitle("状态栏着色");
        setContentView(R.layout.activity_stable1);
    }

}