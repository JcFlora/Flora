package com.jc.flora.apps.scene.album.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Shijincheng on 2019/4/9.
 */

public class PickImage {

    public String imagePath;
    public Bitmap bitmap;
    public Uri uri;

    public void showThumbnail(ImageView iv){
        Glide.with(iv.getContext())
                .asBitmap()
                .load(uri)
                .apply(new RequestOptions().centerCrop())
                .into(iv);
    }

    public void showImage(ImageView iv){
        Glide.with(iv.getContext())
                .load(uri)
                .apply(new RequestOptions().fitCenter())
                .into(iv);
    }

    public Bitmap getBitmap(){
        if(bitmap == null){
            bitmap = BitmapFactory.decodeFile(imagePath);
        }
        return bitmap;
    }

}
