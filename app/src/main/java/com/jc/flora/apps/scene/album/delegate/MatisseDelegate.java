package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择业务管理
 * 需配置7.0FileProvider
 * implementation 'com.zhihu.android:matisse:0.5.2-beta3'
 * Created by Shijincheng on 2019/4/11.
 */

public class MatisseDelegate extends Fragment {

    private static final int PICK_IMAGE = 101;
    /** 缩略图显示比例 */
    private static final float THUMBNAIL_SCALE = 0.85f;

    /** 图片选择回调 */
    private OnImagePickedCallback mOnImagePickedCallback;
    /** 拍照的FileProvider策略 */
    private CaptureStrategy mCaptureStrategy;
    /** Glide4图片显示引擎 */
    private Glide4Engine mGlide4Engine = new Glide4Engine();

    /** 拍照模式 */
    private boolean mCaptureEnable = false;
    /** 多选模式 */
    private boolean mIsMultiMode = true;
    /** 数字标记 */
    private boolean mCountable = false;
    /** 主题 */
    private int mStyle = R.style.Matisse_Zhihu;

    public void setCaptureEnable(boolean captureEnable) {
        mCaptureEnable = captureEnable;
    }

    public void setMultiMode(boolean multiMode) {
        mIsMultiMode = multiMode;
    }

    public void setCountable(boolean countable) {
        mCountable = countable;
    }

    public void setStyle(int style) {
        mStyle = style;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            mCaptureStrategy = new CaptureStrategy(true,activity.getPackageName()+".fileProvider");
        }
    }

    public void addToActivityNow(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
            mCaptureStrategy = new CaptureStrategy(true,activity.getPackageName()+".fileProvider");
        }
    }

    /**
     * 打开相册
     * @param maxSelectable 最多选择图片数量
     * @param callback 图片批量裁剪完的回调
     */
    public void openAlbum(int maxSelectable, OnImagePickedCallback callback){
        mOnImagePickedCallback = callback;
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .capture(mCaptureEnable)
                .captureStrategy(mCaptureStrategy)
                .countable(mIsMultiMode && mCountable)
                .maxSelectable(mIsMultiMode ? maxSelectable : 1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(THUMBNAIL_SCALE)
                .theme(mStyle)
                .imageEngine(mGlide4Engine)
                .forResult(PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            // 将新拍的照片添加到系统相册
            addPhotoToGallery(data);
            // 处理图片选择结果
            handlePickImageList(data);
        }else{
            // 处理取消选择的情况
            handleCancel();
        }
    }

    /**
     * 将新拍的照片添加到系统相册（Matisse拍照默认不会添加到相册）
     * @param data
     */
    private void addPhotoToGallery(Intent data) {
        List<String> pathList = Matisse.obtainPathResult(data);
        if(pathList.size() == 1) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(new File(pathList.get(0))));
            getContext().sendBroadcast(mediaScanIntent);
        }
    }

    /**
     * 处理图片选择结果
     * @param data
     */
    private void handlePickImageList(Intent data){
        List<Uri> uriList = Matisse.obtainResult(data);
        List<String> pathList = Matisse.obtainPathResult(data);
        ArrayList<PickImage> imageList = new ArrayList<>(uriList.size());
        for (int i = 0, size = uriList.size(); i < size; i++) {
            PickImage image = new PickImage();
            image.uri = uriList.get(i);
            image.imagePath = pathList.get(i);
            imageList.add(image);
        }
        if(mOnImagePickedCallback != null){
            mOnImagePickedCallback.onImagePicked(imageList);
        }
    }

    /**
     * 处理取消选择的情况
     */
    private void handleCancel() {
        if (mOnImagePickedCallback != null) {
            mOnImagePickedCallback.onCancel();
        }
    }

    /**
     * 图片选择回调
     */
    public interface OnImagePickedCallback {
        void onImagePicked(ArrayList<PickImage> imageList);
        void onCancel();
    }

}