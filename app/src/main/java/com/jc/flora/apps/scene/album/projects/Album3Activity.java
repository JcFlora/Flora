package com.jc.flora.apps.scene.album.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.GalleryDelegate;
import com.jc.flora.apps.scene.album.delegate.CaptureDelegate;
import com.jc.flora.apps.scene.album.delegate.CompressDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;

/**
 * Created by shijincheng on 2019/4/9.
 */
public class Album3Activity extends AppCompatActivity {

    private TextView mTvImagePath, mTvImageCompressPath;
    private ImageView mIvImage;
    private GalleryDelegate mGalleryDelegate;
    private CaptureDelegate mCaptureDelegate;
    private CompressDelegate mCompressDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加图片压缩功能");
        setContentView(R.layout.activity_album3);
        findViews();
        initGalleryDelegate();
        initCaptureDelegate();
        initCompressDelegate();
    }

    private void findViews(){
        mTvImagePath = findViewById(R.id.tv_image_path);
        mTvImageCompressPath = findViewById(R.id.tv_image_compress_path);
        mIvImage = findViewById(R.id.iv_image);
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

    public void openFileChooser(View v){
        mGalleryDelegate.openFileChooser(mOnImagePickedCallback);
    }

    public void openAlbum(View v){
        mGalleryDelegate.openAlbum(mOnImagePickedCallback);
    }

    public void openCamera(View v){
        mCaptureDelegate.openCamera(mOnCapturedCallback);
    }

    public void pickImage(View v){
        showListDialog();
    }

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
                openFileChooser(null);
                break;
            case 1 :
                openAlbum(null);
                break;
            case 2 :
                openCamera(null);
                break;
        }
    }

    private GalleryDelegate.OnImagePickedCallback mOnImagePickedCallback = new GalleryDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(PickImage image) {
            // 显示图片路径
            mTvImagePath.setText("相册选择图片原图路径：" + image.imagePath);
            // 开始压缩
            mCompressDelegate.compressImage(image, mOnImageCompressedCallback);
        }
    };

    private CaptureDelegate.OnCapturedCallback mOnCapturedCallback = new CaptureDelegate.OnCapturedCallback() {
        @Override
        public void onCaptured(PickImage image) {
            // 显示图片路径
            mTvImagePath.setText("拍照图片原图路径：" + image.imagePath);
            // 开始压缩
            mCompressDelegate.compressImage(image, mOnImageCompressedCallback);
        }
    };

    private CompressDelegate.OnImageCompressedCallback mOnImageCompressedCallback = new CompressDelegate.OnImageCompressedCallback() {
        @Override
        public void onImageCompressed(PickImage image) {
            // 显示图片路径
            mTvImageCompressPath.setText("压缩后图片路径：" + image.imagePath);
            // 显示图片
            image.showImage(mIvImage);
        }
    };

}