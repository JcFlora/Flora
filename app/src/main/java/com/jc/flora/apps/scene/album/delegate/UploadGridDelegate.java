package com.jc.flora.apps.scene.album.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.apps.scene.album.adapter.UploadGridAdapter;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;

/**
 * 网格列表图片上传业务管理
 * Created by shijincheng on 2019/4/10.
 */
public class UploadGridDelegate {

    /** 最大上传数量 */
    private static final int MAX_UPLOAD = 12;

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 图片网格 */
    private RecyclerView mRvUploadGrid;
    /** 上传图片提示文字 */
    private TextView mTvUploadPhotoHint;
    /** 当前图片数据适配器 */
    private UploadGridAdapter mAdapter;
    /** 图片列表 */
    private ArrayList<PickImage> mPhotoList = new ArrayList<>(MAX_UPLOAD);

    private AddPhotoBridge mAddPhotoBridge;
    private PreviewPhotoBridge mPreviewPhotoBridge;

    public UploadGridDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setRvUploadGrid(RecyclerView rvUploadGrid) {
        mRvUploadGrid = rvUploadGrid;
    }

    public void setTvUploadPhotoHint(TextView tvUploadPhotoHint) {
        mTvUploadPhotoHint = tvUploadPhotoHint;
    }

    public void setAddPhotoBridge(AddPhotoBridge addPhotoBridge) {
        mAddPhotoBridge = addPhotoBridge;
    }

    public void setPreviewPhotoBridge(PreviewPhotoBridge previewPhotoBridge) {
        mPreviewPhotoBridge = previewPhotoBridge;
    }

    public void init() {
        initViews();
    }

    public void onPhotoPicked(ArrayList<PickImage> lst){
        mPhotoList.addAll(lst);
        mAdapter.notifyDataSetChanged();
        refreshUploadHint();
    }

    public boolean hasSelectPhoto() {
        boolean hasSelectPhoto = mPhotoList.size() > 0;
        if(!hasSelectPhoto){
            ToastDelegate.show(mActivity, "请添加图片，至少1张");
        }
        return hasSelectPhoto;
    }

    public ArrayList<PickImage> getPhotoList() {
        return mPhotoList;
    }

    private void initViews(){
        mAdapter = new UploadGridAdapter(mPhotoList, MAX_UPLOAD);
        mAdapter.setOnItemClickListener(new UploadGridAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                if(mAddPhotoBridge != null){
                    mAddPhotoBridge.startAddPhoto(MAX_UPLOAD - mPhotoList.size());
                }
            }
            @Override
            public void onPhotoClick(int position) {
                if(mPreviewPhotoBridge != null){
                    mPreviewPhotoBridge.previewPhoto(mPhotoList, position);
                }
            }
            @Override
            public void onDeleteClick(int position) {
                showRemovePicDialog(position);
            }
        });
        mRvUploadGrid.setAdapter(mAdapter);
    }

    private void showRemovePicDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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

    private void removePic(int position) {
        mPhotoList.remove(position);
        mAdapter.notifyDataSetChanged();
        refreshUploadHint();
    }

    private void refreshUploadHint() {
        mTvUploadPhotoHint.setVisibility(mAdapter.hasPickMax() ? View.GONE : View.VISIBLE);
    }

    public interface AddPhotoBridge{
        void startAddPhoto(int addCount);
    }

    public interface PreviewPhotoBridge{
        void previewPhoto(ArrayList<PickImage> photoList, int index);
    }

}
