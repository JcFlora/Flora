package com.jc.flora.apps.scene.bluetooth.projects;

import android.bluetooth.BluetoothDevice;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
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
public class BluetoothDevicesAdapter4 extends RecyclerView.Adapter<BluetoothDevicesAdapter4.ViewHolder> {

    private AppCompatActivity mActivity;
    private List<BluetoothDevice> mData;

    public BluetoothDevicesAdapter4(AppCompatActivity activity, List<BluetoothDevice> data) {
        mActivity = activity;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        String name = mData.get(index).getName();
        if(TextUtils.isEmpty(name)){
            name = mData.get(index).getAddress();
        }
        holder.tvTitle.setText(name);
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

}
