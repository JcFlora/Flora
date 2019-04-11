package com.jc.flora.apps.scene.album.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/10.
 */
public class UploadGridAdapter extends RecyclerView.Adapter<UploadGridAdapter.ViewHolder> {

    /** 最大上传数量 */
    private final int PHOTO_MAX_COUNT;

    private ArrayList<PickImage> mData;
    private OnItemClickListener mOnItemClickListener;

    public UploadGridAdapter(ArrayList<PickImage> data, int photoMaxCount) {
        mData = data == null? new ArrayList<PickImage>() : data;
        PHOTO_MAX_COUNT = photoMaxCount;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public boolean hasPickMax(){
        return getPhotoCount() >= PHOTO_MAX_COUNT;
    }

    @Override
    public int getItemCount() {
        return mData.size() < PHOTO_MAX_COUNT ? mData.size() + 1 : PHOTO_MAX_COUNT;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UploadGridAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_upload_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onDeleteClick(index);
                }
            }
        });
        if (isPhoto(position)) {
            holder.ivPhoto.setImageBitmap(mData.get(index).bitmap);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onPhotoClick(index);
                    }
                }
            });
        } else {
            holder.ivPhoto.setImageResource(R.drawable.album_add_pic);
            holder.btnDelete.setVisibility(View.GONE);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onAddClick(index);
                    }
                }
            });
        }
    }

    private boolean isPhoto(int position) {
        return position < getPhotoCount();
    }

    private int getPhotoCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPhoto;
        private View btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

    }

    public interface OnItemClickListener{
        void onAddClick(int position);
        void onPhotoClick(int position);
        void onDeleteClick(int position);
    }

}
