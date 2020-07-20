package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.album.projects.SingleUCropActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * implementation 'com.github.yalantis:ucrop:2.2.2'
 * Created by Shijincheng on 2019/4/14.
 */
public class MatisseAndSingleUCropDelegate extends Fragment {

    private OnImageCroppedCallback mOnImageCroppedCallback;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void openAlbum(OnImageCroppedCallback callback) {
        mOnImageCroppedCallback = callback;
        SingleUCropActivity.route(this, UCrop.REQUEST_CROP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            Uri resultUri = UCrop.getOutput(data);
            PickImage image = new PickImage();
            image.imagePath = resultUri.getPath();
            image.uri = AlbumUtils.getUriFromFile(getContext(), new File(image.imagePath));
            if(mOnImageCroppedCallback != null){
                mOnImageCroppedCallback.onImageCropped(image);
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