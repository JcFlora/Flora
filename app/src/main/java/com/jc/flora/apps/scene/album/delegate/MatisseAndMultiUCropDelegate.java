package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.album.projects.MultiUCropResultListActivity;

import java.util.ArrayList;

/**
 * 图片选择与批量裁剪合并业务管理
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by Shijincheng on 2019/4/15.
 */
public class MatisseAndMultiUCropDelegate extends Fragment {

    private static final int PICK_IMAGE = 101;

    /** 图片批量裁剪完的回调 */
    private OnImageCroppedCallback mOnImageCroppedCallback;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    /**
     * 打开相册
     * @param maxSelectable 最多选择图片数量
     * @param callback 图片批量裁剪完的回调
     */
    public void openAlbum(int maxSelectable, OnImageCroppedCallback callback) {
        mOnImageCroppedCallback = callback;
        MultiUCropResultListActivity.route(this, maxSelectable, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(mOnImageCroppedCallback != null){
                mOnImageCroppedCallback.onImageCropped(UCropGridDelegate.getSaveImageList(getContext(), data));
            }
        }
    }

    /**
     * 图片批量裁剪完的回调
     */
    public interface OnImageCroppedCallback {
        void onImageCropped(ArrayList<PickImage> imageList);
    }

}