package com.jc.flora.launcher;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.List;

/**
 * 入口App数据适配器
 * Created by shijincheng on 2017/6/12.
 */
public class LauncherCardsAdapter extends RecyclerView.Adapter<LauncherCardsAdapter.ViewHolder> {

    private Activity mActivity;
    private List<LauncherApp> mData;

    public LauncherCardsAdapter(Activity activity, List<LauncherApp> data) {
        mActivity = activity;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_launcher_apps_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvTitle.setText(mData.get(index).appName);
        holder.ivIcon.setImageResource(mData.get(index).appIconResId);
        holder.vItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,mData.get(index).targetActivity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View vItem;
        /** 图标 */
        public ImageView ivIcon;
        /** App名称 */
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            vItem = itemView;
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }
}