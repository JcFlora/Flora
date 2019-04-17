package com.jc.flora.apps.scene.album.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.MatisseDelegate;
import com.jc.flora.apps.scene.album.delegate.UCropGridDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.util.ArrayList;

/**
 * 批量裁剪列表页面
 * Created by shijincheng on 2019/4/15.
 */
public class MultiUCropResultListActivity extends AppCompatActivity {

    /** 最大选择数量 */
    private int mMaxSelectable;
    /** 照片列表 */
    private RecyclerView mRvPhoto;
    /** 显示大图 */
    private ImageView mIvBigImage;
    /** 裁剪按钮 */
    private View mBtnCrop;
    /** 保存裁剪列表结果按钮 */
    private View mBtnSaveAll;

    /** 图片选择业务管理 */
    private MatisseDelegate mMatisseDelegate;
    /** 网格列表图片裁剪业务管理 */
    private UCropGridDelegate mUCropGridDelegate;

    /**
     * 启动批量裁剪列表页面
     * @param fragment
     * @param maxSelectable 最大选择数量
     * @param requestCode
     */
    public static void route(Fragment fragment, int maxSelectable, int requestCode){
        Intent intent = new Intent(fragment.getContext(), MultiUCropResultListActivity.class);
        intent.putExtra("maxSelectable", maxSelectable);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("裁剪结果");
        setContentView(R.layout.activity_album_multi_ucrop);
        initData();
        findViews();
        initMatisseDelegate();
        initUCropGridDelegate();
        openAlbum();
    }

    private void initData(){
        mMaxSelectable = getIntent().getIntExtra("maxSelectable", 0);
    }

    private void findViews(){
        mRvPhoto = findViewById(R.id.rv_photo);
        mRvPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mIvBigImage = findViewById(R.id.iv_big_image);
        mBtnCrop = findViewById(R.id.btn_crop);
        mBtnSaveAll = findViewById(R.id.btn_save_all);
    }

    private void initMatisseDelegate(){
        mMatisseDelegate = new MatisseDelegate();
        mMatisseDelegate.setCaptureEnable(true);
        mMatisseDelegate.setMultiMode(true);
        mMatisseDelegate.setCountable(true);
        mMatisseDelegate.addToActivityNow(this, "matisse");
    }

    private void initUCropGridDelegate(){
        mUCropGridDelegate = new UCropGridDelegate();
        mUCropGridDelegate.setRvUCropGrid(mRvPhoto);
        mUCropGridDelegate.setBtnCrop(mBtnCrop);
        mUCropGridDelegate.setBtnSaveAll(mBtnSaveAll);
        mUCropGridDelegate.setPreviewPhotoBridge(mPreviewPhotoBridge);
        mUCropGridDelegate.addToActivity(this, "uCropGrid");
    }

    private void openAlbum(){
        // 开始选图
        mMatisseDelegate.openAlbum(mMaxSelectable, mOnImagePickedCallback);
    }

    /**
     * 选完图片的回调
     */
    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            // 刷新图片显示列表（原图）
            mUCropGridDelegate.onPhotoPicked(imageList);
            imageList.get(0).showImage(mIvBigImage);
        }

        @Override
        public void onCancel() {
            finish();
        }
    };

    /**
     * 查看大图的连接桥
     */
    private UCropGridDelegate.PreviewPhotoBridge mPreviewPhotoBridge = new UCropGridDelegate.PreviewPhotoBridge() {
        @Override
        public void previewPhoto(ArrayList<PickImage> photoList, int index) {
            if(photoList.isEmpty()){
                finish();
            }else{
                photoList.get(index).showImage(mIvBigImage);
            }
        }
    };

}