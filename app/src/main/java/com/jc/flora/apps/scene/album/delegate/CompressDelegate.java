package com.jc.flora.apps.scene.album.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;

/**
 * Created by Shijincheng on 2019/4/9.
 */

public class CompressDelegate extends Fragment {

    //private static final String COMPRESS_SAVE_PATH = FolderUtils.getAppFolderPath() + "album/";
    private static final String COMPRESS_FILE_PRE = "compress_";

    private Context mContext;
    private String mSavePath = "";
    private OnImageCompressedCallback mOnImageCompressedCallback;
    private CompressImageTask mCompressImageTask;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mContext = activity;
            mSavePath = FolderUtils.getAppFolderPath(mContext) + "album/";
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
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
            String fileName =  COMPRESS_FILE_PRE + System.currentTimeMillis() +".jpg";
            FolderUtils.createFile(mSavePath, fileName);
            PickImage pickImage = new PickImage();
            pickImage.imagePath = mSavePath + fileName;
            pickImage.bitmap = AlbumUtils.getCompressImage(pickImages[0].imagePath, pickImage.imagePath);
            pickImage.uri = AlbumUtils.getUriFromFile(getContext(), new File(mSavePath, fileName));
            return pickImage;
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