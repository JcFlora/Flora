package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.delegate.CompressDelegate;
import com.jc.flora.apps.scene.album.delegate.CropDelegate;
import com.jc.flora.apps.scene.album.delegate.MatisseDelegate;
import com.jc.flora.apps.scene.album.delegate.UploadGridDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.preview.projects.SingleEasyPreviewActivity;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;

import java.util.ArrayList;

/**
 * implementation 'com.zhihu.android:matisse:0.5.2-beta3'
 * Created by shijincheng on 2019/4/11.
 */
public class Matisse1Activity extends AppCompatActivity {

    private RecyclerView mRvPhoto;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    private UploadGridDelegate mUploadGridDelegate;
    private CompressDelegate mCompressDelegate;
    private CropDelegate mCropDelegate;
    private MatisseDelegate mMatisseDelegate;
    private ProgressDialogDelegate mProgressDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Matisse实现单选功能");
        setContentView(R.layout.activity_album_matisse1);
        findViews();
        initUploadGridDelegate();
        initMatisseDelegate();
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

    private void initMatisseDelegate(){
        mMatisseDelegate = new MatisseDelegate();
        mMatisseDelegate.setMultiMode(false);
        mMatisseDelegate.addToActivity(this, "matisse");
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
            SingleEasyPreviewActivity.route(Matisse1Activity.this, photoList.get(index).uri);
        }
    };

    private UploadGridDelegate.AddPhotoBridge mAddPhotoBridge = new UploadGridDelegate.AddPhotoBridge() {
        @Override
        public void startAddPhoto(int addCount) {
            // 开始选图
            mMatisseDelegate.openAlbum(addCount, mOnImagePickedCallback);
        }
    };

    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            // 开始压缩
            mCompressDelegate.compressImage(imageList.get(0), mOnImageCompressedCallback);
        }

        @Override
        public void onCancel() {
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
                    FolderUtils.delete(FolderUtils.getAppFolderPath() + "album/");
                    finish();
                }
            },1500);
        }
    }

}