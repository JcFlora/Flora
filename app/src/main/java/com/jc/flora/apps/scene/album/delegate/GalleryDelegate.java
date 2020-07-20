package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.scene.album.model.PickImage;

/**
 * Created by Shijincheng on 2019/4/8.
 */

public class GalleryDelegate extends Fragment {

    private static final int PICK_IMAGE = 101;

    private Intent mFileChooserIntent = new Intent();
    private Intent mAlbumIntent = new Intent();

    private PickImage mPickImage;
    private OnImagePickedCallback mOnImagePickedCallback;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mFileChooserIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }else{
            mFileChooserIntent.setAction(Intent.ACTION_GET_CONTENT);
        }
        mFileChooserIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mFileChooserIntent.setType("image/*");

        mAlbumIntent.setAction(Intent.ACTION_PICK);
        mAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
    }

    public void openFileChooser(OnImagePickedCallback callback){
        mOnImagePickedCallback = callback;
        mPickImage = new PickImage();
        startActivityForResult(mFileChooserIntent, PICK_IMAGE);
    }

    public void openAlbum(OnImagePickedCallback callback){
        mOnImagePickedCallback = callback;
        mPickImage = new PickImage();
        startActivityForResult(mAlbumIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            handlePickImage(data.getData());
        }
    }

    private void handlePickImage(Uri uri){
        //判断手机系统版本号
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4及以上系统使用这个方法处理图片
            mPickImage.imagePath = AlbumUtils.handleImagePathOnKitKat(getContext(), uri);
        } else {
            //4.4以下系统使用这个方法处理图片
            mPickImage.imagePath = AlbumUtils.handleImagePathBeforeKitKat(getContext(), uri);
        }
        mPickImage.uri = uri;
        if(mOnImagePickedCallback != null){
            mOnImagePickedCallback.onImagePicked(mPickImage);
        }
    }

    public interface OnImagePickedCallback {
        void onImagePicked(PickImage image);
    }

}