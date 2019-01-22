package com.jc.flora.apps.component.image.projects;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jc.flora.R;

import java.lang.reflect.Field;

/**
 * Created by shijincheng on 2017/4/17.
 */
public class Gif2Activity extends AppCompatActivity {

    private ImageView mIv1, mIv2;
    private boolean mIsFirstLoadGif = true;
    private GifDrawable mGifDrawable;
    private GifDecoder mGifDecoder;

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
        // fixme 已知bug?：低版本运行或Gif图片帧率过高时，会播放两遍
        loadOneTimeGif(this, R.drawable.image_gif_stroke, mIv2);
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
     * fixme 依然存在bug?：锁屏后唤醒屏幕仍会重新播放Gif图片
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(!mIsFirstLoadGif && mGifDrawable != null){
            // 获取最后一帧的索引
            int lastFrameIndex = mGifDecoder.getFrameCount() - 1;
            // 获取当前帧的索引
            int currentFrameIndex = mGifDecoder.getCurrentFrameIndex();
            // 如果是停止状态或者在最后一帧的位置，停止Gif图片的播放
            if(currentFrameIndex == -1 || currentFrameIndex == lastFrameIndex){
                mGifDrawable.stop();
            }
        }
        mIsFirstLoadGif = false;
    }

    /**
     * 加载Gif并只播放一次
     * @param context
     * @param resourceId
     * @param imageView
     */
    private void loadOneTimeGif(Context context, @RawRes @DrawableRes @Nullable Integer resourceId,
                                final ImageView imageView) {
        //关闭缓存，在连续播放多个相同的Gif时，会出现第二个Gif是从最后一帧开始播放的。
        RequestOptions option = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).asGif().load(resourceId).apply(option).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                mGifDrawable = resource;
                mGifDecoder = getDecoderFromDrawable(resource);
                //设置只播放一次
                resource.setLoopCount(1);
                return false;
            }
        }).into(imageView);
    }

    /**
     * 加载Gif并只播放一次
     * @param context
     * @param resourceId
     * @param imageView
     * @param gifListener
     */
    private void loadOneTimeGif(Context context, @RawRes @DrawableRes @Nullable Integer resourceId,
                                final ImageView imageView, final GifListener gifListener) {
        //关闭缓存，在连续播放多个相同的Gif时，会出现第二个Gif是从最后一帧开始播放的。
        RequestOptions option = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).asGif().load(resourceId).apply(option).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                mGifDrawable = resource;
                mGifDecoder = getDecoderFromDrawable(resource);
                //设置只播放一次
                resource.setLoopCount(1);
                if(mGifDecoder == null){
                    return false;
                }
                //获得总帧数
                int count = resource.getFrameCount();
                int delay = 0;
                for (int i = 0; i < count; i++) {
                    //计算每一帧所需要的时间进行累加
                    delay += mGifDecoder.getDelay(i);
                }
                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (gifListener != null) {
                            gifListener.gifPlayComplete();
                        }
                    }
                }, delay);
                return false;
            }
        }).into(imageView);
    }

    /**
     * 通过反射的方法从GifDrawable中取得GifDecoder
     * @param gifDrawable
     * @return
     */
    private GifDecoder getDecoderFromDrawable(GifDrawable gifDrawable){
        try {
            Field gifStateField = GifDrawable.class.getDeclaredField("state");
            gifStateField.setAccessible(true);
            Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
            Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
            gifFrameLoaderField.setAccessible(true);
            Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
            Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
            gifDecoderField.setAccessible(true);
            return (GifDecoder) gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(gifDrawable)));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }

}