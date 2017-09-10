package com.jc.flora.apps.ui.bezier.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/9/7.
 */
public class Bezier2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("拐点跟随手指移动的2阶贝塞尔曲线");
        setContentView(R.layout.activity_bezier2);
    }

}