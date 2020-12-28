package com.jc.flora.apps.scene.album.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.delegate.CaptureDelegate;
import com.jc.flora.apps.scene.album.delegate.CompressDelegate;
import com.jc.flora.apps.scene.album.delegate.CropDelegate;
import com.jc.flora.apps.scene.album.delegate.GalleryDelegate;
import com.jc.flora.apps.scene.album.delegate.UploadGridDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.preview.projects.SingleEasyPreviewActivity;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/10.
 */
public class Album5Activity extends AppCompatActivity {

    private RecyclerView mRvPhoto;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    private UploadGridDelegate mUploadGridDelegate;
    private GalleryDelegate mGalleryDelegate;
    private CaptureDelegate mCaptureDelegate;
    private CompressDelegate mCompressDelegate;
    private CropDelegate mCropDelegate;
    private ProgressDialogDelegate mProgressDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("列表展示选择结果+大图预览");
        setContentView(R.layout.activity_album5);
        findViews();
        initUploadGridDelegate();
        initGalleryDelegate();
        initCaptureDelegate();
        initCompressDelegate();
        initCropDelegate();
        initProgressDialogDelegate();
    }

    private void findViews(){
        mRvPhoto = findViewById(R.id.rv_photo);
        mTvUploadPhotoHint = findViewById(R.id.tv_upload_photo_hint);
        mRvPhoto.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void initUploadGridDelegate(){
        mUploadGridDelegate = new UploadGridDelegate(this);
        mUploadGridDelegate.setRvUploadGrid(mRvPhoto);
        mUploadGridDelegate.setTvUploadPhotoHint(mTvUploadPhotoHint);
        mUploadGridDelegate.setPreviewPhotoBridge(mPreviewPhotoBridge);
        mUploadGridDelegate.setAddPhotoBridge(mAddPhotoBridge);
        mUploadGridDelegate.init();
    }

    private void initGalleryDelegate(){
        mGalleryDelegate = new GalleryDelegate();
        mGalleryDelegate.init();
        mGalleryDelegate.addToActivity(this, "gallery");
    }

    private void initCaptureDelegate(){
        mCaptureDelegate = new CaptureDelegate();
        mCaptureDelegate.addToActivity(this, "capture");
    }

    private void initCompressDelegate(){
        mCompressDelegate = new CompressDelegate();
        mCompressDelegate.addToActivity(this, "compress");
    }

    private void initCropDelegate(){
        mCropDelegate = new CropDelegate();
        mCropDelegate.init();
        mCropDelegate.addToActivity(this, "crop");
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private UploadGridDelegate.PreviewPhotoBridge mPreviewPhotoBridge = new UploadGridDelegate.PreviewPhotoBridge() {
        @Override
        public void previewPhoto(ArrayList<PickImage> photoList, int index) {
            SingleEasyPreviewActivity.route(Album5Activity.this, photoList.get(index).uri);
        }
    };

    private UploadGridDelegate.AddPhotoBridge mAddPhotoBridge = new UploadGridDelegate.AddPhotoBridge() {
        @Override
        public void startAddPhoto(int addCount) {
            showListDialog();
        }
    };

    private void showListDialog() {
        final String[] ITEMS = {"照片", "相册", "拍照", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择照片");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                pickImage(which);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void pickImage(int which){
        switch (which){
            case 0 :
                mGalleryDelegate.openFileChooser(mOnImagePickedCallback);
                break;
            case 1 :
                mGalleryDelegate.openAlbum(mOnImagePickedCallback);
                break;
            case 2 :
                mCaptureDelegate.openCamera(mOnCapturedCallback);
                break;
        }
    }

    private GalleryDelegate.OnImagePickedCallback mOnImagePickedCallback = new GalleryDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(PickImage image) {
            // 开始压缩
            mCompressDelegate.compressImage(image, mOnImageCompressedCallback);
        }
    };

    private CaptureDelegate.OnCapturedCallback mOnCapturedCallback = new CaptureDelegate.OnCapturedCallback() {
        @Override
        public void onCaptured(PickImage image) {
            // 开始压缩
            mCompressDelegate.compressImage(image, mOnImageCompressedCallback);
        }
    };

    private CompressDelegate.OnImageCompressedCallback mOnImageCompressedCallback = new CompressDelegate.OnImageCompressedCallback() {
        @Override
        public void onImageCompressed(PickImage image) {
            // 开始裁剪
            mCropDelegate.cropImage(image, mOnImageCroppedCallback);
        }
    };

    private CropDelegate.OnImageCroppedCallback mOnImageCroppedCallback = new CropDelegate.OnImageCroppedCallback() {
        @Override
        public void onImageCropped(PickImage image) {
            // 刷新图片显示列表
            ArrayList<PickImage> list = new ArrayList<>(1);
            list.add(image);
            mUploadGridDelegate.onPhotoPicked(list);
        }
    };

    public void mockUploadPhoto(View v){
        if(mUploadGridDelegate.hasSelectPhoto()){
            mProgressDialogDelegate.showLoadingDialog();
            mRvPhoto.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialogDelegate.hideLoadingDialog();
                    FolderUtils.delete(FolderUtils.getAppFolderPath(Album5Activity.this) + "album/");
                    finish();
                }
            },1500);
        }
    }

}