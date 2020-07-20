package com.jc.flora.apps.scene.album.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
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

    /** 查看大图的连接桥 */
    private PreviewPhotoBridge mPreviewPhotoBridge;
    /** 添加图片的连接桥 */
    private AddPhotoBridge mAddPhotoBridge;

    public UploadGridDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setRvUploadGrid(RecyclerView rvUploadGrid) {
        mRvUploadGrid = rvUploadGrid;
    }

    public void setTvUploadPhotoHint(TextView tvUploadPhotoHint) {
        mTvUploadPhotoHint = tvUploadPhotoHint;
    }

    public void setPreviewPhotoBridge(PreviewPhotoBridge previewPhotoBridge) {
        mPreviewPhotoBridge = previewPhotoBridge;
    }

    public void setAddPhotoBridge(AddPhotoBridge addPhotoBridge) {
        mAddPhotoBridge = addPhotoBridge;
    }

    public void init() {
        initViews();
    }

    /**
     * 图片选好之后，刷新图片列表
     * @param list
     */
    public void onPhotoPicked(ArrayList<PickImage> list){
        mPhotoList.addAll(list);
        mAdapter.notifyDataSetChanged();
        refreshUploadHint();
    }

    /**
     * 是否选择了图片，有图片返回true，没图片返回false，并且提示
     * @return
     */
    public boolean hasSelectPhoto() {
        boolean hasSelectPhoto = mPhotoList.size() > 0;
        if(!hasSelectPhoto){
            ToastDelegate.show(mActivity, "请添加图片，至少1张");
        }
        return hasSelectPhoto;
    }

    /**
     * 获取选择的图片
     * @return
     */
    public ArrayList<PickImage> getPhotoList() {
        return mPhotoList;
    }

    /**
     * 初始化列表
     */
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

    /**
     * 显示删除图片对话框
     * @param position 图片索引
     */
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

    /**
     * 删除图片
     * @param position
     */
    private void removePic(int position) {
        mPhotoList.remove(position);
        mAdapter.notifyDataSetChanged();
        refreshUploadHint();
    }

    /**
     * 刷新上传提示文字
     */
    private void refreshUploadHint() {
        mTvUploadPhotoHint.setVisibility(mAdapter.hasPickMax() ? View.GONE : View.VISIBLE);
    }

    /**
     * 查看大图的连接桥
     */
    public interface PreviewPhotoBridge{
        void previewPhoto(ArrayList<PickImage> photoList, int index);
    }

    /**
     * 添加图片的连接桥
     */
    public interface AddPhotoBridge{
        void startAddPhoto(int addCount);
    }

}