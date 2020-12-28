package com.jc.flora.apps.scene.album.delegate;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
 * 图片压缩业务管理
 * implementation 'top.zibin:Luban:1.1.8'
 * Created by Shijincheng on 2019/4/13.
 */
public class LubanDelegate extends Fragment {

//    /** 图片保存路径 */
//    private static final String COMPRESS_SAVE_PATH = FolderUtils.getAppFolderPath() + "album";
    /** 图片保存名称前缀 */
    private static final String COMPRESS_FILE_PRE = "compress_";

    private Context mContext;
    /** 图片保存路径 */
    private String mSavePath = "";
    /** 所有图片压缩完的回调 */
    private OnLubanCompressedCallback mOnLubanCompressedCallback;
    /** 图片列表 */
    private ArrayList<PickImage> mPickImageList;
    /** 当前正在压缩的图片索引 */
    private int mCurrentComposeIndex = -1;
    /** 压缩是否出错 */
    private boolean mHasError = false;
    /** 图片个数 */
    private int mSize = 0;

    /**
     * 初始化图片保存路径
     */
    public void init(){
//        new File(COMPRESS_SAVE_PATH + "/").mkdirs();
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mContext = activity;
            mSavePath = FolderUtils.getAppFolderPath(mContext) + "album";
            new File(mSavePath + "/").mkdirs();
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    /**
     * 开始压缩图片
     * @param imageList  原始图片列表
     * @param callback   压缩完毕的回调
     */
    public void compressImage(ArrayList<PickImage> imageList, OnLubanCompressedCallback callback) {
        if(imageList == null || imageList.isEmpty()){
            return;
        }
        mCurrentComposeIndex = 0;
        mOnLubanCompressedCallback = callback;
        mSize = imageList.size();
        mPickImageList = new ArrayList<>(mSize);
        ArrayList<String> pathList = new ArrayList<>(mSize);
        for (PickImage pickImage : imageList) {
            pathList.add(pickImage.imagePath);
        }
        compressImage(pathList);
    }

    /**
     * 开始压缩图片
     * @param pathList 原始图片路径列表
     */
    private void compressImage(final ArrayList<String> pathList) {
        Luban.with(getContext())
                .load(pathList)
                .ignoreBy(100)
                .setTargetDir(mSavePath)
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
                })
                .launch();
    }

    /**
     * 每一张图片压缩开始时的回调
     */
    private void onCompressStart() {
        if(mCurrentComposeIndex == 0 && mOnLubanCompressedCallback != null){
            mOnLubanCompressedCallback.onStart();
        }
        mCurrentComposeIndex++;
    }

    /**
     * 每一张图片压缩成功后的回调
     * @param file 压缩后的文件
     */
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

    /**
     * 每一张图片压缩出错的回调
     */
    private void onCompressError() {
        mHasError = true;
        if(mCurrentComposeIndex == mSize && mOnLubanCompressedCallback != null){
            mOnLubanCompressedCallback.onError();
        }
    }

    /**
     * 所有图片压缩完的回调
     */
    public interface OnLubanCompressedCallback {
        void onStart();
        void onSuccess(ArrayList<PickImage> imageList);
        void onError();
    }

}