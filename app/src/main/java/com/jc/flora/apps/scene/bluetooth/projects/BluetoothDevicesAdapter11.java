package com.jc.flora.apps.scene.bluetooth.projects;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.List;

/**
 * Created by shijincheng on 2017/1/12.
 */
public class BluetoothDevicesAdapter11 extends RecyclerView.Adapter<BluetoothDevicesAdapter11.ViewHolder> {

    private AppCompatActivity mActivity;
    private List<BluetoothDevice> mData;

    private OnItemClickListener mOnItemClickListener;

    public BluetoothDevicesAdapter11(AppCompatActivity activity, List<BluetoothDevice> data) {
        mActivity = activity;
        mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        String name = mData.get(index).getName();
        if(TextUtils.isEmpty(name)){
            name = mData.get(index).getAddress();
        }
        holder.tvTitle.setText(name);
        if(mOnItemClickListener != null){
            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /** 设备名称 */
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
