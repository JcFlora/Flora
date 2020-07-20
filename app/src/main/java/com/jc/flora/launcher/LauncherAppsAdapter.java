package com.jc.flora.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.List;

/**
 * 入口App数据适配器
 * Created by shijincheng on 2017/1/12.
 */
public class LauncherAppsAdapter extends RecyclerView.Adapter<LauncherAppsAdapter.ViewHolder> {

    private Activity mActivity;
    private List<LauncherApp> mData;
    private ColorMatrixColorFilter mGrayFilter;

    public LauncherAppsAdapter(Activity activity, List<LauncherApp> data) {
        mActivity = activity;
        mData = data;
        initGrayFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_launcher_apps_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvTitle.setText(mData.get(index).appName);
        holder.ivIcon.setColorFilter(getColorFilter(index));
        holder.ivIcon.setImageResource(mData.get(index).appIconResId);
        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,  mData.get(index).targetActivity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private void initGrayFilter(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        mGrayFilter = new ColorMatrixColorFilter(matrix);
    }

    private ColorFilter getColorFilter(int index){
        boolean isNotFoundActivity = mData.get(index).targetActivity.isAssignableFrom(NotFoundActivity.class);
        return isNotFoundActivity ? mGrayFilter : null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /** 图标 */
        public ImageView ivIcon;
        /** App名称 */
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }
}