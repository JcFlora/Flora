package com.jc.flora.apps.scene.album.delegate;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.album.projects.SingleUCropActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by Shijincheng on 2019/4/14.
 */
public class MatisseAndSingleUCropDelegate extends Fragment {

    private PickImage mPickImage;
    private OnImageCroppedCallback mOnImageCroppedCallback;
    private Intent mIntent = new Intent();

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            mIntent.setClass(activity, SingleUCropActivity.class);
        }
    }

    public void openAlbum(OnImageCroppedCallback callback) {
        mPickImage = new PickImage();
        mOnImageCroppedCallback = callback;
        startActivityForResult(mIntent, UCrop.REQUEST_CROP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            mPickImage.imagePath = resultUri.getPath();
            mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(mPickImage.imagePath));
            if(mOnImageCroppedCallback != null){
                mOnImageCroppedCallback.onImageCropped(mPickImage);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            if(mOnImageCroppedCallback != null){
                mOnImageCroppedCallback.onError();
            }
        }
    }

    public interface OnImageCroppedCallback {
        void onImageCropped(PickImage image);
        void onError();
    }

}
