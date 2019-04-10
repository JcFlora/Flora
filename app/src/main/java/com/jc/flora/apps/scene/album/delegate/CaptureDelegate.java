package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * 需配置7.0FileProvider
 * Created by Shijincheng on 2019/4/8.
 */

public class CaptureDelegate extends Fragment {

    private static final int CAPTURE_PHOTO = 102;
    private static final String CAPTURE_SAVE_PATH = FolderUtils.getAppFolderPath();

    private Intent mIntent = new Intent();
    private PickImage mPickImage = new PickImage();
    private String mCaptureFileName = "album_capture.jpg";
    private OnCapturedCallback mOnCapturedCallback;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(String captureFileName){
        if(!TextUtils.isEmpty(captureFileName)){
            mCaptureFileName = captureFileName;
        }
        mPickImage.imagePath = CAPTURE_SAVE_PATH + mCaptureFileName;
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public void openCamera(OnCapturedCallback callback){
        mOnCapturedCallback = callback;
        FolderUtils.delete(CAPTURE_SAVE_PATH + mCaptureFileName);
        FolderUtils.createFile(CAPTURE_SAVE_PATH, mCaptureFileName);
        mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(CAPTURE_SAVE_PATH, mCaptureFileName));
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPickImage.uri);
        startActivityForResult(mIntent, CAPTURE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_PHOTO && resultCode == Activity.RESULT_OK) {
            handleCapturePhoto();
        }
    }

    private void handleCapturePhoto(){
        if(mOnCapturedCallback != null){
            mPickImage.bitmap = BitmapFactory.decodeFile(mPickImage.imagePath);
            mOnCapturedCallback.onCaptured(mPickImage);
        }
    }

    public interface OnCapturedCallback {
        void onCaptured(PickImage image);
    }

}