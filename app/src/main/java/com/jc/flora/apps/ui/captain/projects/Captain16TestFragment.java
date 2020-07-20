package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.reload.widget.AutoSwipeRefreshLayout;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Captain16TestFragment extends CaptainFragment{

    private String mTitle;
    private TextView mTvTitle;
    /** 下拉刷新布局 */
    private AutoSwipeRefreshLayout mSrlContent;

    public void setTitle(String title) {
        mTitle = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_captain16_fragment, container, false);
        mSrlContent = (AutoSwipeRefreshLayout) v.findViewById(R.id.srl_content);
        mTvTitle = (TextView) v.findViewById(R.id.tv_title);
        if(!TextUtils.isEmpty(mTitle)){
            mTvTitle.setText(mTitle);
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置下拉刷新色调
        mSrlContent.setColorSchemeResources(
                R.color.colorAccent,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 设置下拉刷新事件
        mSrlContent.setOnRefreshListener(new AutoSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataAndUi();
            }
        });
        mSrlContent.autoRefresh();
    }

    private void refreshDataAndUi() {
        mSrlContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
                mSrlContent.setRefreshing(false);
                ToastDelegate.show(getActivity(),"刷新数据");
            }
        },1500);
    }

    @Override
    public void onReload(int position) {
        mSrlContent.autoRefresh();
    }

}