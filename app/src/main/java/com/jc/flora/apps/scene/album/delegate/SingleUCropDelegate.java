package com.jc.flora.apps.scene.album.delegate;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.UCropFragment;

import java.io.File;

/**
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by Shijincheng on 2019/4/14.
 */
public class SingleUCropDelegate {

    private static final String CROP_SAVE_PATH = FolderUtils.getAppFolderPath() + "album/";
    private static final String CROP_FILE_PRE = "crop_";

    private AppCompatActivity mActivity;
//    private PickImage mPickImage;
    private UCrop.Options mOptions = new UCrop.Options();
    private UCropFragment mUCropFragment;

    public SingleUCropDelegate(AppCompatActivity activity) {
        mActivity = activity;
        initOptions();
    }

    public void setBtnCrop(View btnCrop) {
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCropFragment.cropAndSaveImage();
            }
        });
    }

    public void cropImage(PickImage image) {
        String fileName =  CROP_FILE_PRE + System.currentTimeMillis() +".jpg";
        FolderUtils.createFile(CROP_SAVE_PATH, fileName);
//        mPickImage = new PickImage();
//        mPickImage.imagePath = CROP_SAVE_PATH + fileName;
//        mPickImage.uri = AlbumUtils.getUriFromFile(mActivity, new File(CROP_SAVE_PATH, fileName));
        Uri source = Uri.fromFile(new File(image.imagePath));
        Uri destination = Uri.fromFile(new File(CROP_SAVE_PATH + fileName));
        UCrop uCrop = UCrop.of(source, destination)
                .withAspectRatio(1,1)
                .withOptions(mOptions);
        mUCropFragment = uCrop.getFragment();
        // 设置根控制器
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_fragment, mUCropFragment, "rootFragment")
                .commitAllowingStateLoss();
//        initFragment();
    }

    private void initOptions(){
        // 设置控制面板高亮颜色
        mOptions.setActiveWidgetColor(mActivity.getResources().getColor(R.color.colorPrimary));
//        // 使用圆形裁剪
//        mOptions.setCircleDimmedLayer(true);
//        // 圆形裁剪，隐藏矩形外边框
//        mOptions.setShowCropFrame(false);
//        // 圆形裁剪，隐藏矩形内部网格
//        mOptions.setShowCropGrid(false);
        // 设置手势控制权限
        mOptions.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.SCALE);
        // 设置不允许调整裁剪宽高比例
        mOptions.setFreeStyleCropEnabled(false);
    }

    private void initFragment(){
//        cropFragment.getView().findViewById(R.id.controls_wrapper).setBackgroundColor(Color.WHITE);
//        cropFragment.getView().findViewById(R.id.wrapper_states).setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
    }

    public void onCropFinish(UCropFragment.UCropResult result) {
        mActivity.setResult(result.mResultCode, result.mResultData);
        mActivity.finish();
    }
}