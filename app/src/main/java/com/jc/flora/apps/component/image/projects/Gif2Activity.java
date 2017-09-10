package com.jc.flora.apps.component.image.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/4/17.
 */
public class Gif2Activity extends AppCompatActivity {

    private ImageView mIv1, mIv2;
    private boolean mIsFirstLoadGif = true;
    private GifDrawable mGifDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用Glide加载Gif");
        setContentView(R.layout.activity_image_gif1);
        findViews();
        initViews();
    }

    private void findViews() {
        mIv1 = (ImageView) findViewById(R.id.iv_gif1);
        mIv2 = (ImageView) findViewById(R.id.iv_gif2);
    }

    private void initViews() {
        Glide.with(this).load(R.drawable.image_gif_logo).into(mIv1);
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(mIv2, 1){
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                mGifDrawable = (GifDrawable) resource;
            }
        };
        Glide.with(this).load(R.drawable.image_gif_stroke).into(target);
        mIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGifDrawable != null){
                    mGifDrawable.start();
                }
            }
        });
    }

    /**
     * Glide加载Gif图片，会在onStart时自动重新播放Gif图片
     * 解决此bug的方案是在onStart后，判断当前Gif图片是否正在播放
     * 如果没有正在播放，就停止Gif的重新播放
     * fixme 依然存在bug：黑屏后仍会重新播放
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(!mIsFirstLoadGif && mGifDrawable != null){
            // 获取GifDecoder对象
            GifDecoder decoder = mGifDrawable.getDecoder();
            // 获取最后一帧的索引
            int lastFrameIndex = decoder.getFrameCount() - 1;
            // 获取当前帧的索引
            int currentFrameIndex = decoder.getCurrentFrameIndex();
            // 如果是停止状态或者在最后一帧的位置，停止Gif图片的播放
            if(currentFrameIndex == -1 || currentFrameIndex == lastFrameIndex){
                mGifDrawable.stop();
            }
        }
        mIsFirstLoadGif = false;
    }

    @Override
    protected void onDestroy() {
        if(mGifDrawable != null){
            mGifDrawable.recycle();
            mGifDrawable = null;
        }
        super.onDestroy();
    }
}