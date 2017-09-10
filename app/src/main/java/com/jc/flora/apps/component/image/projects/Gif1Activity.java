package com.jc.flora.apps.component.image.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jc.flora.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * compile 'pl.droidsonroids.gif:android-gif-drawable:1.2.8'
 * Created by shijincheng on 2017/4/17.
 */
public class Gif1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("加载Gif");
        setContentView(R.layout.activity_image_gif1);
        initViews();
    }

    private void initViews(){
        ImageView iv = (ImageView) findViewById(R.id.iv_gif1);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(iv, params);
        try {
            final GifDrawable drawable = new GifDrawable(getResources(), R.drawable.image_gif_logo);
            drawable.setLoopCount(1);
            iv.setImageDrawable(drawable);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!drawable.isRunning()){
                        drawable.reset();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}