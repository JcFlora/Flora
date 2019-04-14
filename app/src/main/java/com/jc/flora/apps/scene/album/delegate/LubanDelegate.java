package com.jc.flora.apps.scene.album.delegate;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * implementation 'top.zibin:Luban:1.1.8'
 * Created by Shijincheng on 2019/4/13.
 */
public class LubanDelegate extends Fragment {

    private static final String COMPRESS_SAVE_PATH = FolderUtils.getAppFolderPath() + "album";
    private static final String COMPRESS_FILE_PRE = "compress_";

    private OnLubanCompressedCallback mOnLubanCompressedCallback;
    private ArrayList<PickImage> mPickImageList;
    private int mCurrentComposeIndex = -1;
    private boolean mHasError = false;
    private int mSize = 0;

    public void init(){
        new File(COMPRESS_SAVE_PATH+"/").mkdirs();
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void compressImage(ArrayList<PickImage> imageList, OnLubanCompressedCallback callback) {
        if(imageList == null || imageList.isEmpty()){
            return;
        }
        mSize = imageList.size();
        mPickImageList = new ArrayList<>(mSize);
        mOnLubanCompressedCallback = callback;
        mCurrentComposeIndex = 0;
        ArrayList<String> pathList = new ArrayList<>(mSize);
        for (PickImage pickImage : imageList) {
            pathList.add(pickImage.imagePath);
        }
        compressImage(pathList);
    }

    private void compressImage(final ArrayList<String> pathList) {
        Luban.with(getContext())
                .load(pathList)
                .ignoreBy(100)
                .setTargetDir(COMPRESS_SAVE_PATH)
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return COMPRESS_FILE_PRE + System.currentTimeMillis() +".jpg";
                    }
                })
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        onCompressStart();
                    }
                    @Override
                    public void onSuccess(File file) {
                        onCompressSuccess(file);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onCompressError();
                    }
                }).launch();
    }

    private void onCompressStart() {
        if(mCurrentComposeIndex == 0 && mOnLubanCompressedCallback != null){
            mOnLubanCompressedCallback.onStart();
        }
        mCurrentComposeIndex++;
    }

    private void onCompressSuccess(File file) {
        PickImage pickImage = new PickImage();
        pickImage.imagePath = file.getAbsolutePath();
        pickImage.uri = AlbumUtils.getUriFromFile(getContext(), file);
        mPickImageList.add(pickImage);
        if(mCurrentComposeIndex == mSize && mOnLubanCompressedCallback != null){
            if(mHasError){
                mOnLubanCompressedCallback.onError();
            }else{
                mOnLubanCompressedCallback.onSuccess(mPickImageList);
            }
        }
    }

    private void onCompressError() {
        mHasError = true;
        if(mCurrentComposeIndex == mSize && mOnLubanCompressedCallback != null){
            mOnLubanCompressedCallback.onError();
        }
    }

    public interface OnLubanCompressedCallback {
        void onStart();
        void onSuccess(ArrayList<PickImage> imageList);
        void onError();
    }

}