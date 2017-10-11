package com.jc.flora.apps.component.audio.model;

/**
 * Created by shijincheng on 2017/10/7.
 */

public class MP3 {

    public String name;
    public int resId;
    public int coverImgResId;

    public MP3(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public MP3(String name, int resId, int coverImgResId) {
        this.name = name;
        this.resId = resId;
        this.coverImgResId = coverImgResId;
    }
}
