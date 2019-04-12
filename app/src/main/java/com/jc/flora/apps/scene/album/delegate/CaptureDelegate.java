package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * 需配置7.0FileProvider
 * Created by Shijincheng on 2019/4/8.
 */

public class CaptureDelegate extends Fragment {

    private static final int CAPTURE_PHOTO = 102;
    private static final String CAPTURE_SAVE_PATH = FolderUtils.getAppFolderPath() + "album/";
    private static final String CAPTURE_FILE_PRE = "capture_";

    private PickImage mPickImage;
    private OnCapturedCallback mOnCapturedCallback;
    private Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void openCamera(OnCapturedCallback callback){
        mOnCapturedCallback = callback;
        String fileName =  CAPTURE_FILE_PRE + System.currentTimeMillis() +".jpg";
        FolderUtils.createFile(CAPTURE_SAVE_PATH, fileName);
        mPickImage = new PickImage();
        mPickImage.imagePath = CAPTURE_SAVE_PATH + fileName;
        mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(CAPTURE_SAVE_PATH, fileName));
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
            mOnCapturedCallback.onCaptured(mPickImage);
        }
    }

    public interface OnCapturedCallback {
        void onCaptured(PickImage image);
    }

}