package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * compile 'com.mostafagazar:customshapeimageview:1.0.4'
 * https://github.com/MostafaGazar/CustomShapeImageView
 * Created by Samurai on 2017/8/15.
 */
public class ShapeCustomImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自定义形状图片（CustomShapeImageView）");
        setContentView(R.layout.activity_shape_custom_image);
    }

}