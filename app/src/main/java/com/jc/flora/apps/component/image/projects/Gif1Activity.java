package com.jc.flora.apps.component.image.projects;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * compile 'pl.droidsonroids.gif:android-gif-drawable:1.2.8'
 * Created by shijincheng on 2017/4/17.
 */
public class Gif1Activity extends AppCompatActivity {

    private ImageView mIv1, mIv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用GifDrawable加载Gif");
        setContentView(R.layout.activity_image_gif1);
        findViews();
        initViews();
    }

    private void findViews(){
        mIv1 = (ImageView) findViewById(R.id.iv_gif1);
        mIv2 = (ImageView) findViewById(R.id.iv_gif2);
    }

    private void initViews(){
        // 循环播放的Gif图片
        final GifDrawable drawable1 = getGifDrawable(R.drawable.image_gif_logo);
        if(drawable1 != null){
            mIv1.setImageDrawable(drawable1);
        }
        // 只播放一次的Gif图片
        final GifDrawable drawable2 = getGifDrawable(R.drawable.image_gif_stroke);
        if(drawable2 != null){
            drawable2.setLoopCount(1);
            mIv2.setImageDrawable(drawable2);
            mIv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!drawable2.isRunning()){
                        drawable2.reset();
                    }
                }
            });
        }
    }

    private GifDrawable getGifDrawable(@DrawableRes int drawableId){
        try {
            return new GifDrawable(getResources(), drawableId);
        } catch (IOException e) {
            return null;
        }
    }

}