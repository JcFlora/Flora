package com.jc.flora.apps.component.audio.model;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by shijincheng on 2017/10/7.
 */

public class MP3 {

    public String name;
    public int resId;
    public int coverImgResId;
    public String audioAlbum;
    public String audioUrl;
    public String audioCapacity = "";
    public String id = "";
    // 模拟支付拦截时用来判断是否免费，如果不免费，会触发支付回调
    public boolean isFree;
    public String audioLocalPath = "";

    public MP3(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public MP3(String name, int resId, int coverImgResId) {
        this.name = name;
        this.resId = resId;
        this.coverImgResId = coverImgResId;
    }

    public MP3(String name, String audioAlbum, String audioUrl) {
        this.name = name;
        this.audioAlbum = audioAlbum;
        this.audioUrl = audioUrl;
    }

    public MP3(String name, String audioAlbum, String audioUrl, String audioCapacity) {
        this(name, audioAlbum, audioUrl);
        this.audioCapacity = audioCapacity;
    }

    public MP3(String name, String audioAlbum, String id, Boolean isFree) {
        this.name = name;
        this.audioAlbum = audioAlbum;
        this.id = id;
        this.isFree = isFree;
    }

    public Uri getAudioUri(Context context){
        if(resId > 0){
            String audioPath = "android.resource://" + context.getPackageName() + "/" + resId;
            // 这个本地Uri不适用于ExoPlayer
            return Uri.parse(audioPath);
        }else if(!TextUtils.isEmpty(audioUrl)){
            return Uri.parse(audioUrl);
        }
        return null;
    }

    public void loadAlbum(ImageView ivAlbum){
        if(coverImgResId > 0){
            ivAlbum.setImageResource(coverImgResId);
        }else if(!TextUtils.isEmpty(audioAlbum)){
            Glide.with(ivAlbum).load(audioAlbum).into(ivAlbum);
        }
    }

    /**
     * 是否能播放
     * @return
     */
    public boolean couldPlay(){
        return isFree && !TextUtils.isEmpty(audioUrl);
    }

}