package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.delegate.LubanDelegate;
import com.jc.flora.apps.scene.album.delegate.MatisseAndMultiUCropDelegate;
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
 * Created by shijincheng on 2019/4/15.
 */
public class Matisse7Activity extends AppCompatActivity {

    /** 照片列表 */
    private RecyclerView mRvPhoto;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    /** 网格列表图片上传业务管理 */
    private UploadGridDelegate mUploadGridDelegate;
    /** 图片选择与批量裁剪合并业务管理 */
    private MatisseAndMultiUCropDelegate mMatisseAndMultiUCropDelegate;
    /** loading框业务管理 */
    private ProgressDialogDelegate mProgressDialogDelegate;
    /** 图片压缩业务管理 */
    private LubanDelegate mLubanDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("结合Ucrop实现批量裁剪");
        setContentView(R.layout.activity_album_matisse1);
        findViews();
        initUploadGridDelegate();
        initMatisseAndMultiUCropDelegate();
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

    private void initMatisseAndMultiUCropDelegate(){
        mMatisseAndMultiUCropDelegate = new MatisseAndMultiUCropDelegate();
        mMatisseAndMultiUCropDelegate.addToActivity(this, "matisseAndMultiUCrop");
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private void initLubanDelegate(){
        mLubanDelegate = new LubanDelegate();
        mLubanDelegate.init();
        mLubanDelegate.addToActivity(this, "luban");
    }

    /**
     * 查看大图的连接桥
     */
    private UploadGridDelegate.PreviewPhotoBridge mPreviewPhotoBridge = new UploadGridDelegate.PreviewPhotoBridge() {
        @Override
        public void previewPhoto(ArrayList<PickImage> photoList, int index) {
            SingleEasyPreviewActivity.route(Matisse7Activity.this, photoList.get(index).uri);
        }
    };

    /**
     * 添加图片的连接桥
     */
    private UploadGridDelegate.AddPhotoBridge mAddPhotoBridge = new UploadGridDelegate.AddPhotoBridge() {
        @Override
        public void startAddPhoto(int addCount) {
            // 开始选图和裁剪
            mMatisseAndMultiUCropDelegate.openAlbum(addCount, mOnImageCroppedCallback);
        }
    };

    /**
     * 图片批量裁剪完的回调
     */
    private MatisseAndMultiUCropDelegate.OnImageCroppedCallback mOnImageCroppedCallback = new MatisseAndMultiUCropDelegate.OnImageCroppedCallback() {
        @Override
        public void onImageCropped(ArrayList<PickImage> imageList) {
            // 开始批量压缩
            mLubanDelegate.compressImage(imageList, mOnLubanCompressedCallback);
        }
    };

    /**
     * 压缩完毕的回调
     */
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
            ToastDelegate.show(Matisse7Activity.this, "压缩错误");
        }
    };

    public void mockUploadPhoto(View v){
        // 模拟图片上传
        if(mUploadGridDelegate.hasSelectPhoto()){
            mProgressDialogDelegate.showLoadingDialog();
            mRvPhoto.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialogDelegate.hideLoadingDialog();
                    FolderUtils.delete(FolderUtils.getAppFolderPath(Matisse7Activity.this) + "album/");
                    finish();
                }
            },1500);
        }
    }

}