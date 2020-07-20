package com.jc.flora.apps.scene.album.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.delegate.GalleryDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;

/**
 * Created by shijincheng on 2019/4/6.
 */
public class Album1Activity extends AppCompatActivity {

    private TextView mTvImagePath;
    private ImageView mIvImage;
    private GalleryDelegate mGalleryDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用系统相册");
        setContentView(R.layout.activity_album1);
        findViews();
        initDelegate();
    }

    private void findViews(){
        mTvImagePath = findViewById(R.id.tv_image_path);
        mIvImage = findViewById(R.id.iv_image);
    }

    private void initDelegate(){
        mGalleryDelegate = new GalleryDelegate();
        mGalleryDelegate.init();
        mGalleryDelegate.addToActivity(this, "gallery");
    }

    public void openFileChooser(View v){
        mGalleryDelegate.openFileChooser(mOnImagePickedCallback);
    }

    public void openAlbum(View v){
        mGalleryDelegate.openAlbum(mOnImagePickedCallback);
    }

    private GalleryDelegate.OnImagePickedCallback mOnImagePickedCallback = new GalleryDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(PickImage image) {
            // 显示图片路径
            mTvImagePath.setText("相册选择图片路径：" + image.imagePath);
            // 显示图片
            image.showImage(mIvImage);
        }
    };

}