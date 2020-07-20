package com.jc.flora.apps.scene.album.projects;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.MatisseDelegate;
import com.jc.flora.apps.scene.album.delegate.SingleUCropDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.util.ArrayList;

/**
 * 图片裁剪页面（单个）
 * Created by shijincheng on 2019/4/14.
 */
public class SingleUCropActivity extends AppCompatActivity implements UCropFragmentCallback {

    /** 图片选择业务管理 */
    private MatisseDelegate mMatisseDelegate;
    /** 图片裁剪业务管理 */
    private SingleUCropDelegate mSingleUCropDelegate;
    /** loading框业务管理 */
    private ProgressDialogDelegate mProgressDialogDelegate;

    /**
     * 启动单个图片裁剪页面（没有图片源，会直接跳转到图片选择页面先选图片）
     * @param fragment
     * @param requestCode
     */
    public static void route(Fragment fragment, int requestCode) {
        route(fragment, null, requestCode);
    }

    /**
     * 启动单个图片裁剪页面
     * @param fragment
     * @param imagePath    原始图片路径
     * @param requestCode
     */
    public static void route(Fragment fragment, String imagePath, int requestCode){
        Intent intent = new Intent(fragment.getContext(), SingleUCropActivity.class);
        intent.putExtra("imagePath", imagePath);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("裁剪");
        setContentView(R.layout.activity_album_single_ucrop);
        initMatisseDelegate();
        initSingleUCropDelegate();
        initProgressDialogDelegate();
        openAlbumOrCropImage();
    }

    private void initMatisseDelegate(){
        mMatisseDelegate = new MatisseDelegate();
        mMatisseDelegate.setCaptureEnable(true);
        mMatisseDelegate.setMultiMode(false);
        mMatisseDelegate.addToActivityNow(this, "matisse");
    }

    private void initSingleUCropDelegate(){
        mSingleUCropDelegate = new SingleUCropDelegate(this);
        mSingleUCropDelegate.setBtnSave(findViewById(R.id.btn_save));
    }

    private void initProgressDialogDelegate(){
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
    }

    private void openAlbumOrCropImage(){
        // 获取原始图片路径
        String imagePath = getIntent().getStringExtra("imagePath");
        // 根据有没有原始图，决定是否先去选图
        if(TextUtils.isEmpty(imagePath)){
            // 开始选图
            mMatisseDelegate.openAlbum(1, mOnImagePickedCallback);
        }else{
            // 开始裁剪
            mSingleUCropDelegate.cropImage(imagePath);
        }
    }

    /**
     * 选完图片的回调
     */
    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            // 开始裁剪
            mSingleUCropDelegate.cropImage(imageList.get(0));
        }

        @Override
        public void onCancel() {
            finish();
        }
    };

    /**
     * 裁剪过程中处理Loading交互
     * @param showLoader
     */
    @Override
    public void loadingProgress(boolean showLoader) {
        if(showLoader){
            mProgressDialogDelegate.showLoadingDialog();
        }else{
            mProgressDialogDelegate.hideLoadingDialog();
        }
    }

    /**
     * UCropFragment的裁剪完毕回调
     * @param result
     */
    @Override
    public void onCropFinish(UCropFragment.UCropResult result) {
        setResult(result.mResultCode, result.mResultData);
        finish();
    }

}