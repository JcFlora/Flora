package com.jc.flora.apps.ui.list.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.list.model.Slip3;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/9/4.
 */
public class RadioAdapter extends BaseAdapter{

    private LayoutInflater mLayoutInflater;
    /** 显示数据 */
    private ArrayList<Slip3> mSlipList;

    /** 当前选中索引 */
    private int mSelectedIndex = 0;

    public RadioAdapter(Context context, ArrayList<Slip3> slipList) {
        mLayoutInflater = LayoutInflater.from(context);
        mSlipList = slipList;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
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
    public int getItemViewType(int position) {
        return mSlipList == null ? 0 : mSlipList.get(position).slipType.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Slip3.SlipType.values().length;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_radio, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvSubtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
            holder.rb = (AppCompatRadioButton) convertView.findViewById(R.id.rb);
            holder.rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(position != mSelectedIndex){
                        ViewHolder vh = (ViewHolder) parent.getChildAt(mSelectedIndex).getTag();
                        vh.rb.setChecked(false);
                        mSelectedIndex = position;
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.tvTitle.setText(mSlipList.get(position).title);
        holder.tvSubtitle.setText(mSlipList.get(position).subTitle);
        holder.rb.setChecked(position == mSelectedIndex);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView ivIcon;
        private TextView  tvTitle;
        private TextView  tvSubtitle;
        private AppCompatRadioButton rb;
    }

}