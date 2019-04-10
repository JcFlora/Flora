package com.jc.flora.apps.scene.album.delegate;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * Created by Shijincheng on 2019/4/9.
 */

public class CompressDelegate extends Fragment {

    private static final String COMPRESS_SAVE_PATH = FolderUtils.getAppFolderPath();

    private PickImage mPickImage = new PickImage();
    private String mCompressFileName = "album_compress.jpg";
    private OnImageCompressedCallback mOnImageCompressedCallback;
    private CompressImageTask mCompressImageTask;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void init(String compressFileName){
        if(!TextUtils.isEmpty(compressFileName)){
            mCompressFileName = compressFileName;
        }
    }

    public void compressImage(PickImage image, OnImageCompressedCallback callback){
        mOnImageCompressedCallback = callback;
        mCompressImageTask = new CompressImageTask();
        mCompressImageTask.execute(image);
    }

    @SuppressLint("StaticFieldLeak")
    private class CompressImageTask extends AsyncTask<PickImage, Void, PickImage>{

        @Override
        protected PickImage doInBackground(PickImage... pickImages) {
            FolderUtils.delete(COMPRESS_SAVE_PATH + mCompressFileName);
            FolderUtils.createFile(COMPRESS_SAVE_PATH, mCompressFileName);
            mPickImage.imagePath = COMPRESS_SAVE_PATH + mCompressFileName;
            mPickImage.bitmap = AlbumUtils.getCompressImage(pickImages[0].imagePath, mPickImage.imagePath);
            mPickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(COMPRESS_SAVE_PATH, mCompressFileName));
            return mPickImage;
        }

        @Override
        protected void onPostExecute(PickImage image) {
            if(mOnImageCompressedCallback != null){
                mOnImageCompressedCallback.onImageCompressed(image);
            }
        }
    }

    public interface OnImageCompressedCallback {
        void onImageCompressed(PickImage image);
    }

    @Override
    public void onDestroy() {
        if(mCompressImageTask != null){
            mCompressImageTask.cancel(true);
        }
        super.onDestroy();
    }

}