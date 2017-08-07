package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * compile 'de.hdodenhof:circleimageview:2.1.0'
 * https://github.com/hdodenhof/CircleImageView
 * Created by Samurai on 2017/8/4.
 */
public class ShapeCircleImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("圆角图片（使用开源CircleImageView）");
        setContentView(R.layout.activity_shape_circle_image);
    }

}