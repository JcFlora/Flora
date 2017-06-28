package com.jc.flora.apps.component.image.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/4/17.
 */
public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("加载Gif");
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(iv, params);
        Glide.with(this).load(R.drawable.image_gif).asGif().into(iv);
    }
}
