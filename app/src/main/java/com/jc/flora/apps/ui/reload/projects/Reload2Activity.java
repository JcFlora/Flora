package com.jc.flora.apps.ui.reload.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.reload.widget.AutoSwipeRefreshLayout;

/**
 * Created by Samurai on 2017/5/25.
 */
public class Reload2Activity extends AppCompatActivity {

    /** 下拉刷新布局 */
    private AutoSwipeRefreshLayout mSrlContent;
    /** 刷新数据的任务 */
    private Runnable mStopReloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("实现点击刷新和自动刷新");
        setContentView(R.layout.activity_reload2);
        mSrlContent = (AutoSwipeRefreshLayout) findViewById(R.id.srl_content);
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
        mStopReloadTask = new Runnable() {
            @Override
            public void run() {
                // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
                mSrlContent.setRefreshing(false);
                ToastDelegate.show(Reload2Activity.this, "刷新数据");
            }
        };
        mSrlContent.postDelayed(mStopReloadTask, 1500);
    }

    public void reload(View v){
        mSrlContent.autoRefresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSrlContent.setRefreshing(false);
        mSrlContent.removeCallbacks(mStopReloadTask);
    }

}