package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * 需配置7.0FileProvider
 * Created by Shijincheng on 2019/4/8.
 */

public class CropDelegate extends Fragment {

    private static final int CROP_IMAGE = 103;

    private static final String CROP_SAVE_PATH = FolderUtils.getAppFolderPath();

    private Intent mIntent;
    private PickImage mPickImage = new PickImage();
    private String mCropFileName = "album_crop.jpg";
    private OnImageCroppedCallback mOnImageCroppedCallback;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(String cropFileName){
        if(!TextUtils.isEmpty(cropFileName)){
            mCropFileName = cropFileName;
        }
        mPickImage.imagePath = CROP_SAVE_PATH + mCropFileName;
        mIntent = new Intent("com.android.camera.action.CROP");
        mIntent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        mIntent.putExtra("aspectX", 1);
        mIntent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        mIntent.putExtra("outputX", 320);
        mIntent.putExtra("outputY", 320);
        mIntent.putExtra("outputFormat", "JPEG");// 图片格式
        mIntent.putExtra("noFaceDetection", true);// 取消人脸识别
        mIntent.putExtra("return-data", false);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(CROP_SAVE_PATH + mCropFileName)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public void cropImage(PickImage image, OnImageCroppedCallback callback) {
        mOnImageCroppedCallback = callback;
        FolderUtils.delete(CROP_SAVE_PATH + mCropFileName);
        FolderUtils.createFile(CROP_SAVE_PATH, mCropFileName);
        mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(CROP_SAVE_PATH, mCropFileName));
        mIntent.setDataAndType(image.uri, "image/*");
        startActivityForResult(mIntent, CROP_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CROP_IMAGE && resultCode == Activity.RESULT_OK){
            if(mOnImageCroppedCallback != null){
                mPickImage.bitmap = BitmapFactory.decodeFile(mPickImage.imagePath);
                mOnImageCroppedCallback.onImageCropped(mPickImage);
            }
        }
    }

    public interface OnImageCroppedCallback {
        void onImageCropped(PickImage image);
    }

}