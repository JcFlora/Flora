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
 * 图片裁剪业务管理
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by Shijincheng on 2019/4/14.
 */
public class SingleUCropDelegate {

    /** 图片保存路径 */
    private static final String CROP_SAVE_PATH = FolderUtils.getAppFolderPath() + "album/";
    /** 图片保存名称前缀 */
    private static final String CROP_FILE_PRE = "crop_";

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** UCrop配置参数 */
    private UCrop.Options mOptions = new UCrop.Options();
    /** UCropFragment，展示裁剪布局 */
    private UCropFragment mUCropFragment;

    public SingleUCropDelegate(AppCompatActivity activity) {
        mActivity = activity;
        initOptions();
    }

    /**
     * 设置保存按钮的事件
     * @param btnSave
     */
    public void setBtnSave(View btnSave) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCropFragment.cropAndSaveImage();
            }
        });
    }

    /**
     * 开始裁剪图片
     * @param image 原始图片
     */
    public void cropImage(PickImage image) {
        cropImage(image.imagePath, true);
    }

    /**
     * 开始裁剪图片
     * @param imagePath 原始图片路径
     */
    public void cropImage(String imagePath) {
        cropImage(imagePath, false);
    }

    /**
     * 配置UCrop的参数
     */
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

    /**
     * 开始裁剪图片
     * @param imagePath       原始图片路径
     * @param withAspectRatio 是否限制宽高比例
     */
    private void cropImage(String imagePath, boolean withAspectRatio) {
        // 配置图片路径信息
        String fileName =  CROP_FILE_PRE + System.currentTimeMillis() +".jpg";
        FolderUtils.createFile(CROP_SAVE_PATH, fileName);
        Uri source = Uri.fromFile(new File(imagePath));
        Uri destination = Uri.fromFile(new File(CROP_SAVE_PATH + fileName));
        // 配置UCrop
        UCrop uCrop = UCrop.of(source, destination)
                .withOptions(mOptions);
        if(withAspectRatio){
            uCrop.withAspectRatio(1,1);
        }
        // 配置UCropFragment
        mUCropFragment = uCrop.getFragment();
        // 设置根控制器
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_fragment, mUCropFragment, "rootFragment")
                .commitAllowingStateLoss();
//        initFragment();
    }

    /**
     * 初始化Fragment
     * 如果要调用此方法，添加Fragment的时候，请调用commitNowAllowingStateLoss
     */
//    private void initFragment(){
//        cropFragment.getView().findViewById(R.id.controls_wrapper).setBackgroundColor(Color.WHITE);
//        cropFragment.getView().findViewById(R.id.wrapper_states).setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
//    }

}