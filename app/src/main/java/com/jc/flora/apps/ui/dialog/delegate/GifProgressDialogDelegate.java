package com.jc.flora.apps.ui.dialog.delegate;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jc.flora.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by Samurai on 2019/8/6.
 */
public class GifProgressDialogDelegate {

    private AppCompatActivity mActivity;
    private AppCompatDialog mDialog;
    private ViewHolder mHolder;
    private GifDrawable mGifDrawable;

    public GifProgressDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
        mDialog = new AppCompatDialog(activity);
        onDialogCreated();
        onCreateViewHolder();
        onBindViewHolder();
    }

    public void show(){
        // 循环播放的Gif图片
        mGifDrawable = getGifDrawable(R.drawable.dialog_loading_book);
        mHolder.ivGif.setImageDrawable(mGifDrawable);
        mDialog.show();
    }

    public void hide(){
        mGifDrawable.stop();
        mDialog.dismiss();
    }

    private void onDialogCreated(){
        // 去掉默认的标题栏
        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置自定义布局
        mDialog.setContentView(R.layout.dialog_gif_progress);
        // 设置可关闭
        mDialog.setCancelable(true);
        // 设置背景为透明
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.dimAmount = 0.0f;
        mDialog.getWindow().setAttributes(layoutParams);
    }

    private void onCreateViewHolder(){
        mHolder = new ViewHolder();
        mHolder.ivGif = mDialog.findViewById(R.id.iv_gif);
    }

    private void onBindViewHolder(){

    }

    private GifDrawable getGifDrawable(@DrawableRes int drawableId){
        try {
            return new GifDrawable(mActivity.getResources(), drawableId);
        } catch (IOException e) {
            return null;
        }
    }

    private static class ViewHolder{
        private ImageView ivGif;
    }

}