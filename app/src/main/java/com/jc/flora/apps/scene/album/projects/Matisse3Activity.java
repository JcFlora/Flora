package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
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
public class Matisse3Activity extends AppCompatActivity {

    private RecyclerView mRvPhoto;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    private UploadGridDelegate mUploadGridDelegate;
    private MatisseDelegate mMatisseDelegate;
    private ProgressDialogDelegate mProgressDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Matisse实现多选功能");
        setContentView(R.layout.activity_album_matisse1);
        findViews();
        initUploadGridDelegate();
        initMatisseDelegate();
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
        mMatisseDelegate.setCaptureEnable(true);
        mMatisseDelegate.setMultiMode(true);
        mMatisseDelegate.setCountable(true);
        mMatisseDelegate.addToActivity(this, "matisse");
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private UploadGridDelegate.PreviewPhotoBridge mPreviewPhotoBridge = new UploadGridDelegate.PreviewPhotoBridge() {
        @Override
        public void previewPhoto(ArrayList<PickImage> photoList, int index) {
            SingleEasyPreviewActivity.route(Matisse3Activity.this, photoList.get(index).uri);
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
            // 刷新图片显示列表
            mUploadGridDelegate.onPhotoPicked(imageList);
        }

        @Override
        public void onCancel() {
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