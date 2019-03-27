package com.jc.flora.apps.component.video.model;

import android.content.Context;
import android.net.Uri;

/**
 * Created by shijincheng on 2017/10/7.
 */

public class MP4 {

    public String name;
    public int resId;
    public int coverImgResId;

    public MP4(String name, int resId, int coverImgResId) {
        this.name = name;
        this.resId = resId;
        this.coverImgResId = coverImgResId;
    }

    public Uri getVideoUri(Context context){
        String videoPath = "android.resource://" + context.getPackageName() + "/" + resId;
        return Uri.parse(videoPath);
    }

}
