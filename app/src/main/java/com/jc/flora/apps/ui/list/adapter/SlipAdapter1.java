package com.jc.flora.apps.ui.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.list.model.Slip1;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/9/3.
 */
public class SlipAdapter1 extends BaseAdapter{

    private LayoutInflater mLayoutInflater;
    /** 显示数据 */
    private ArrayList<Slip1> mSlipList;

    public SlipAdapter1(Context context, ArrayList<Slip1> slipList) {
        mLayoutInflater = LayoutInflater.from(context);
        mSlipList = slipList;
    }

    @Override
    public int getCount() {
        return mSlipList == null ? 0 : mSlipList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSlipList == null ? null : mSlipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_basic, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.tvTitle.setText(mSlipList.get(position).title);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView ivIcon;
        private TextView  tvTitle;
    }

}