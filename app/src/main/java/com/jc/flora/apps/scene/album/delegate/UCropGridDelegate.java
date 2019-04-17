package com.jc.flora.apps.scene.album.delegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jc.flora.apps.scene.album.adapter.UCropGridAdapter;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.album.projects.SingleUCropActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

/**
 * 网格列表图片裁剪业务管理
 * Created by shijincheng on 2019/4/15.
 */
public class UCropGridDelegate extends Fragment {

    /** 图片网格 */
    private RecyclerView mRvUCropGrid;
    /** 当前图片数据适配器 */
    private UCropGridAdapter mAdapter;
    /** 图片列表 */
    private ArrayList<PickImage> mPhotoList = new ArrayList<>();
    /** 当前选中的位置 */
    private int mCurrentSelectedIndex = -1;
    /** 查看大图的连接桥 */
    private PreviewPhotoBridge mPreviewPhotoBridge;

    public void setRvUCropGrid(RecyclerView rvUCropGrid) {
        mRvUCropGrid = rvUCropGrid;
    }

    /**
     * 设置裁剪图片点击事件
     * @param btnCrop
     */
    public void setBtnCrop(View btnCrop){
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUCrop();
            }
        });
    }

    /**
     * 设置保存裁剪列表结果点击事件
     * @param btnSaveAll
     */
    public void setBtnSaveAll(View btnSaveAll) {
        btnSaveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAllPhotoList();
            }
        });
    }

    public void setPreviewPhotoBridge(PreviewPhotoBridge previewPhotoBridge) {
        mPreviewPhotoBridge = previewPhotoBridge;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            initViews();
        }
    }

    /**
     * 图片选好之后，刷新图片列表
     * @param list
     */
    public void onPhotoPicked(ArrayList<PickImage> list){
        mPhotoList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

//    public boolean hasPhoto() {
//        return mPhotoList.size() > 0;
//    }
//
//    public ArrayList<PickImage> getPhotoList() {
//        return mPhotoList;
//    }

    /**
     * 初始化列表
     */
    private void initViews(){
        mAdapter = new UCropGridAdapter(mPhotoList);
        mAdapter.setOnItemClickListener(new UCropGridAdapter.OnItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                mCurrentSelectedIndex = position;
                if(mPreviewPhotoBridge != null){
                    mPreviewPhotoBridge.previewPhoto(mPhotoList, position);
                }
            }
            @Override
            public void onDeleteClick(int position) {
                showRemovePicDialog(position);
            }
        });
        mRvUCropGrid.setAdapter(mAdapter);
    }

    /**
     * 显示删除图片对话框
     * @param position 图片索引
     */
    private void showRemovePicDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定要删除这张图片吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                removePic(position);
            }
        });
        builder.show();
    }

    /**
     * 删除图片
     * @param position
     */
    private void removePic(int position) {
        mPhotoList.remove(position);
        mAdapter.notifyDataSetChanged();
        mCurrentSelectedIndex = 0;
        if(mPreviewPhotoBridge != null){
            mPreviewPhotoBridge.previewPhoto(mPhotoList, 0);
        }
    }

    /**
     * 开始裁剪图片（单个）
     */
    public void startUCrop(){
        SingleUCropActivity.route(this, mPhotoList.get(mCurrentSelectedIndex).imagePath, UCrop.REQUEST_CROP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            // 裁剪完毕后，回调获取裁剪的图片，进行替换
            getAndReplaceImage(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {

        }
    }

    /**
     * 获取裁剪的图片，并进行替换
     * @param data 裁剪之后的图片
     */
    private void getAndReplaceImage(Intent data) {
        Uri resultUri = UCrop.getOutput(data);
        PickImage image = new PickImage();
        image.imagePath = resultUri.getPath();
        image.uri = AlbumUtils.getUriFromFile(getContext(), new File(image.imagePath));
        replacePic(image);
    }

    /**
     * 替换裁剪的图片
     * @param image 裁剪之后的图片
     */
    private void replacePic(PickImage image) {
        mPhotoList.set(mCurrentSelectedIndex, image);
        mAdapter.notifyDataSetChanged();
        if(mPreviewPhotoBridge != null){
            mPreviewPhotoBridge.previewPhoto(mPhotoList, mCurrentSelectedIndex);
        }
    }

    /**
     * 保存所有的裁剪之后图片
     */
    public void saveAllPhotoList(){
        Intent intent = new Intent();
        intent.putExtra("uriList", getUriList());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    /**
     * 获取裁剪图片Uri列表
     * @return
     */
    private ArrayList<Uri> getUriList(){
        ArrayList<Uri> uriList = new ArrayList<>(mPhotoList.size());
        for (PickImage image : mPhotoList) {
            uriList.add(Uri.fromFile(new File(image.imagePath)));
        }
        return uriList;
    }

    /**
     * 获取裁剪图片列表
     * @param context
     * @param data
     * @return
     */
    public static ArrayList<PickImage> getSaveImageList(Context context, Intent data){
        ArrayList<Uri> uriList = (ArrayList<Uri>) data.getSerializableExtra("uriList");
        ArrayList<PickImage> imageList = new ArrayList<>(uriList.size());
        for (Uri uri : uriList) {
            PickImage image = new PickImage();
            image.imagePath = uri.getPath();
            image.uri = AlbumUtils.getUriFromFile(context, new File(image.imagePath));
            imageList.add(image);
        }
        return imageList;
    }

    /**
     * 查看大图的连接桥
     */
    public interface PreviewPhotoBridge{
        void previewPhoto(ArrayList<PickImage> photoList, int index);
    }

}