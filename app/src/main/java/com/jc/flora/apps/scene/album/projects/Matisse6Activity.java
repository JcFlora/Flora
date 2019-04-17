package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.delegate.LubanDelegate;
import com.jc.flora.apps.scene.album.delegate.MatisseAndSingleUCropDelegate;
import com.jc.flora.apps.scene.album.delegate.UploadGridDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.preview.projects.SingleEasyPreviewActivity;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;

/**
 * implementation 'com.zhihu.android:matisse:0.5.2-beta3'
 * implementation 'top.zibin:Luban:1.1.8'
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by shijincheng on 2019/4/14.
 */
public class Matisse6Activity extends AppCompatActivity {

    private RecyclerView mRvPhoto;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    private UploadGridDelegate mUploadGridDelegate;
    private MatisseAndSingleUCropDelegate mMatisseAndSingleUCropDelegate;
    private ProgressDialogDelegate mProgressDialogDelegate;
    private LubanDelegate mLubanDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("结合Ucrop实现单个裁剪");
        setContentView(R.layout.activity_album_matisse1);
        findViews();
        initUploadGridDelegate();
        initMatisseAndSingleUCropDelegate();
        initProgressDialogDelegate();
        initLubanDelegate();
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

    private void initMatisseAndSingleUCropDelegate(){
        mMatisseAndSingleUCropDelegate = new MatisseAndSingleUCropDelegate();
        mMatisseAndSingleUCropDelegate.addToActivity(this, "matisseAndSingleUCrop");
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private void initLubanDelegate(){
        mLubanDelegate = new LubanDelegate();
        mLubanDelegate.init();
        mLubanDelegate.addToActivity(this, "luban");
    }

    private UploadGridDelegate.PreviewPhotoBridge mPreviewPhotoBridge = new UploadGridDelegate.PreviewPhotoBridge() {
        @Override
        public void previewPhoto(ArrayList<PickImage> photoList, int index) {
            SingleEasyPreviewActivity.route(Matisse6Activity.this, photoList.get(index).uri);
        }
    };

    private UploadGridDelegate.AddPhotoBridge mAddPhotoBridge = new UploadGridDelegate.AddPhotoBridge() {
        @Override
        public void startAddPhoto(int addCount) {
            // 开始选图和裁剪
            mMatisseAndSingleUCropDelegate.openAlbum(mOnImagePickedCallback);
        }
    };

    private MatisseAndSingleUCropDelegate.OnImageCroppedCallback mOnImagePickedCallback = new MatisseAndSingleUCropDelegate.OnImageCroppedCallback() {
        @Override
        public void onImageCropped(PickImage image) {
            // 开始批量压缩
            ArrayList<PickImage> list = new ArrayList<>(1);
            list.add(image);
            mLubanDelegate.compressImage(list, mOnLubanCompressedCallback);
        }
        @Override
        public void onError() {
            ToastDelegate.show(Matisse6Activity.this, "裁剪失败");
        }
    };

    private LubanDelegate.OnLubanCompressedCallback mOnLubanCompressedCallback = new LubanDelegate.OnLubanCompressedCallback() {
        @Override
        public void onStart() {
            mProgressDialogDelegate.showLoadingDialog();
        }

        @Override
        public void onSuccess(ArrayList<PickImage> imageList) {
            mProgressDialogDelegate.hideLoadingDialog();
            // 刷新图片显示列表
            mUploadGridDelegate.onPhotoPicked(imageList);
        }

        @Override
        public void onError() {
            mProgressDialogDelegate.hideLoadingDialog();
            ToastDelegate.show(Matisse6Activity.this, "压缩错误");
        }
    };

    public void mockUploadPhoto(View v){
        if(mUploadGridDelegate.hasSelectPhoto()){
            mProgressDialogDelegate.showLoadingDialog();
            mRvPhoto.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialogDelegate.hideLoadingDialog();
                    FolderUtils.delete(FolderUtils.getAppFolderPath() + "album/");
                    finish();
                }
            },1500);
        }
    }

}