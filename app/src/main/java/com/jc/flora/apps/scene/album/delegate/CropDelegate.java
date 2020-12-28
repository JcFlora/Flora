package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * 需配置7.0FileProvider
 * Created by Shijincheng on 2019/4/8.
 */

public class CropDelegate extends Fragment {

    private static final int CROP_IMAGE = 103;

//    private static final String CROP_SAVE_PATH = FolderUtils.getAppFolderPath() + "album/";
    private static final String CROP_FILE_PRE = "crop_";

    private Context mContext;
    private String mSavePath = "";
    private PickImage mPickImage;
    private OnImageCroppedCallback mOnImageCroppedCallback;
    private Intent mIntent = new Intent("com.android.camera.action.CROP");

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mContext = activity;
            mSavePath = FolderUtils.getAppFolderPath(mContext) + "album/";
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(){
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public void cropImage(PickImage image, OnImageCroppedCallback callback) {
        mOnImageCroppedCallback = callback;
        String fileName =  CROP_FILE_PRE + System.currentTimeMillis() +".jpg";
        FolderUtils.createFile(mSavePath, fileName);
        mPickImage = new PickImage();
        mPickImage.imagePath = mSavePath + fileName;
        mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(mSavePath, fileName));
        mIntent.setDataAndType(image.uri, "image/*");
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mSavePath + fileName)));
        startActivityForResult(mIntent, CROP_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CROP_IMAGE && resultCode == Activity.RESULT_OK){
            if(mOnImageCroppedCallback != null){
                mOnImageCroppedCallback.onImageCropped(mPickImage);
            }
        }
    }

    public interface OnImageCroppedCallback {
        void onImageCropped(PickImage image);
    }

}