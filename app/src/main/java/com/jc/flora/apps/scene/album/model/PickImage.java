package com.jc.flora.apps.scene.album.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Shijincheng on 2019/4/9.
 */

public class PickImage {

    public String imagePath;
    public Bitmap bitmap;
    public Uri uri;

    public PickImage() {
    }

    public PickImage(Uri uri, String imagePath) {
        this.uri = uri;
        this.imagePath = imagePath;
    }

}
