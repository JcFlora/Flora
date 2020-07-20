package com.jc.flora.apps.scene.album.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.album.model.PickImage;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/4/15.
 */
public class UCropGridAdapter extends RecyclerView.Adapter<UCropGridAdapter.ViewHolder> {

    private ArrayList<PickImage> mData;
    private OnItemClickListener mOnItemClickListener;

    public UCropGridAdapter(ArrayList<PickImage> data) {
        mData = data == null? new ArrayList<PickImage>() : data;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UCropGridAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_upload_grid, parent, false));
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
        mData.get(index).showThumbnail(holder.ivPhoto);
        holder.btnDelete.setVisibility(View.VISIBLE);
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onPhotoClick(index);
                }
            }
        });
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
        void onPhotoClick(int position);
        void onDeleteClick(int position);
    }

}
