package com.jc.flora.apps.scene.album.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.GalleryDelegate;
import com.jc.flora.apps.scene.album.delegate.CaptureDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;

/**
 * Created by shijincheng on 2019/4/8.
 */
public class Album2Activity extends AppCompatActivity {

    private static final String CAPTURE_SAVE_NAME = "album_capture.jpg";

    private TextView mTvImagePath;
    private ImageView mIvImage;
    private GalleryDelegate mGalleryDelegate;
    private CaptureDelegate mCaptureDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用系统拍照");
        setContentView(R.layout.activity_album2);
        findViews();
        initGalleryDelegate();
        initCaptureDelegate();
    }

    private void findViews(){
        mTvImagePath = findViewById(R.id.tv_image_path);
        mIvImage = findViewById(R.id.iv_image);
    }

    private void initGalleryDelegate(){
        mGalleryDelegate = new GalleryDelegate();
        mGalleryDelegate.init();
        mGalleryDelegate.addToActivity(this, "gallery");
    }

    private void initCaptureDelegate(){
        mCaptureDelegate = new CaptureDelegate();
        mCaptureDelegate.init(CAPTURE_SAVE_NAME);
        mCaptureDelegate.addToActivity(this, "capture");
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
            mTvImagePath.setText("相册选择图片路径：" + image.imagePath);
            // 显示图片
            mIvImage.setImageBitmap(image.bitmap);
        }
    };

    private CaptureDelegate.OnCapturedCallback mOnCapturedCallback = new CaptureDelegate.OnCapturedCallback() {
        @Override
        public void onCaptured(PickImage image) {
            // 显示图片路径
            mTvImagePath.setText("拍照图片路径：" + image.imagePath);
            // 显示图片
            mIvImage.setImageBitmap(image.bitmap);
        }
    };

}