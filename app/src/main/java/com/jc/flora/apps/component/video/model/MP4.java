package com.jc.flora.apps.component.video.model;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by shijincheng on 2017/10/7.
 */

public class MP4 {

    public String name;
    public int resId;
    public int coverImgResId;
    public String videoAlbum;
    public String videoUrl;
    public String videoCapacity = "";
    public String videoLocalPath = "";

    public MP4(String name, int resId, int coverImgResId) {
        this.name = name;
        this.resId = resId;
        this.coverImgResId = coverImgResId;
    }

    public MP4(String name, String videoAlbum, String videoUrl) {
        this.name = name;
        this.videoAlbum = videoAlbum;
        this.videoUrl = videoUrl;
    }

    public MP4(String name, String videoAlbum, String videoUrl, String videoCapacity) {
        this(name, videoAlbum, videoUrl);
        this.videoCapacity = videoCapacity;
    }

    public Uri getVideoUri(Context context){
        if(resId > 0){
            String videoPath = "android.resource://" + context.getPackageName() + "/" + resId;
            // 这个本地Uri不适用于ExoPlayer
            return Uri.parse(videoPath);
        }else if(!TextUtils.isEmpty(videoUrl)){
            return Uri.parse(videoUrl);
        }
        return null;
    }

    public void loadAlbum(ImageView ivAlbum){
        if(coverImgResId > 0){
            ivAlbum.setImageResource(coverImgResId);
        }else if(!TextUtils.isEmpty(videoAlbum)){
            Glide.with(ivAlbum).load(videoAlbum).into(ivAlbum);
        }
    }

}
