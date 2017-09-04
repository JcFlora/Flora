package com.jc.flora.apps.ui.list.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
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
public class SlipAdapter3 extends BaseAdapter{

    private LayoutInflater mLayoutInflater;
    /** 显示数据 */
    private ArrayList<Slip3> mSlipList;

    public SlipAdapter3(Context context, ArrayList<Slip3> slipList) {
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
    public int getItemViewType(int position) {
        return mSlipList == null ? 0 : mSlipList.get(position).slipType.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Slip3.SlipType.values().length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        switch (Slip3.SlipType.values()[getItemViewType(position)]){
            case BASIC:
                convertView = getBasicView(position, convertView, parent);
                break;
            case END_SUBTITLE:
                convertView = getEndSubtitleView(position, convertView, parent);
                break;
            case BOTTOM_SUBTITLE:
                convertView = getBottomSubtitleView(position, convertView, parent);
                break;
            case SWITCH:
                convertView = getSwitchView(position, convertView, parent);
                break;
        }
        return convertView;
    }

    private View getBasicView(final int position, View convertView, ViewGroup parent){
        BasicHolder holder;
        if (convertView == null) {
            holder = new BasicHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_basic, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.tvTitle.setText(mSlipList.get(position).title);
        return convertView;
    }

    private View getEndSubtitleView(final int position, View convertView, ViewGroup parent){
        EndSubtitleHolder holder;
        if (convertView == null) {
            holder = new EndSubtitleHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_end_subtitle, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvSubtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
            convertView.setTag(holder);
        } else {
            holder = (EndSubtitleHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.tvTitle.setText(mSlipList.get(position).title);
        holder.tvSubtitle.setText(mSlipList.get(position).subTitle);
        return convertView;
    }

    private View getBottomSubtitleView(final int position, View convertView, ViewGroup parent){
        BottomSubtitleHolder holder;
        if (convertView == null) {
            holder = new BottomSubtitleHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_bottom_subtitle, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvSubtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
            convertView.setTag(holder);
        } else {
            holder = (BottomSubtitleHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.tvTitle.setText(mSlipList.get(position).title);
        holder.tvSubtitle.setText(mSlipList.get(position).subTitle);
        return convertView;
    }

    private View getSwitchView(final int position, View convertView, ViewGroup parent){
        SwitchHolder holder;
        if (convertView == null) {
            holder = new SwitchHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_slip_switch, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.scTitle = (SwitchCompat) convertView.findViewById(R.id.sc_title);
            holder.scTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSlipList.get(position).isChecked = isChecked;
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (SwitchHolder) convertView.getTag();
        }
        holder.ivIcon.setImageResource(mSlipList.get(position).iconResId);
        holder.scTitle.setText(mSlipList.get(position).title);
        holder.scTitle.setChecked(mSlipList.get(position).isChecked);
        return convertView;
    }


    private static class BasicHolder {
        private ImageView ivIcon;
        private TextView  tvTitle;
    }

    private static class EndSubtitleHolder {
        private ImageView ivIcon;
        private TextView  tvTitle;
        private TextView  tvSubtitle;
    }

    private static class BottomSubtitleHolder {
        private ImageView ivIcon;
        private TextView  tvTitle;
        private TextView  tvSubtitle;
    }

    private static class SwitchHolder {
        private ImageView ivIcon;
        private SwitchCompat scTitle;
    }

}