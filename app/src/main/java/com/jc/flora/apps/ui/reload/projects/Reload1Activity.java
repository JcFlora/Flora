package com.jc.flora.apps.ui.reload.projects;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/5/25.
 */
public class Reload1Activity extends AppCompatActivity {

    /** 下拉刷新布局 */
    private SwipeRefreshLayout mSrlContent;
    /** 刷新数据的任务 */
    private Runnable mStopReloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用SwipeRefreshLayout");
        setContentView(R.layout.activity_reload1);
        mSrlContent = (SwipeRefreshLayout) findViewById(R.id.srl_content);
        // 设置下拉刷新色调
        mSrlContent.setColorSchemeResources(
                R.color.colorAccent,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 设置下拉刷新事件
        mSrlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataAndUi();
            }
        });
    }

    private void refreshDataAndUi() {
        mStopReloadTask = new Runnable() {
            @Override
            public void run() {
                // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
                mSrlContent.setRefreshing(false);
                ToastDelegate.show(Reload1Activity.this, "刷新数据");
            }
        };
        mSrlContent.postDelayed(mStopReloadTask, 1500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSrlContent.setRefreshing(false);
        mSrlContent.removeCallbacks(mStopReloadTask);
    }

}