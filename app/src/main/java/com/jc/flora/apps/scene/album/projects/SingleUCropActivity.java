package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.MatisseDelegate;
import com.jc.flora.apps.scene.album.delegate.SingleUCropDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/14.
 */
public class SingleUCropActivity extends AppCompatActivity implements UCropFragmentCallback {

    private MatisseDelegate mMatisseDelegate;
    private SingleUCropDelegate mSingleUCropDelegate;
    private ProgressDialogDelegate mProgressDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("裁剪");
        setContentView(R.layout.activity_album_single_ucrop);
        initMatisseDelegate();
        initSingleUCropDelegate();
        initProgressDialogDelegate();
        openAlbum();
    }

    private void initMatisseDelegate(){
        mMatisseDelegate = new MatisseDelegate();
        mMatisseDelegate.setCaptureEnable(true);
        mMatisseDelegate.setMultiMode(false);
        mMatisseDelegate.addToActivityNow(this, "matisse");
    }

    private void initSingleUCropDelegate(){
        mSingleUCropDelegate = new SingleUCropDelegate(this);
        mSingleUCropDelegate.setBtnCrop(findViewById(R.id.btn_crop));
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private void openAlbum(){
        // 开始选图
        mMatisseDelegate.openAlbum(1, mOnImagePickedCallback);
    }

    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            PickImage image = imageList.get(0);
            mSingleUCropDelegate.cropImage(image);
        }
    };

    @Override
    public void loadingProgress(boolean showLoader) {
        if(showLoader){
            mProgressDialogDelegate.showLoadingDialog();
        }else{
            mProgressDialogDelegate.hideLoadingDialog();
        }
    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult result) {
        mSingleUCropDelegate.onCropFinish(result);
    }
}