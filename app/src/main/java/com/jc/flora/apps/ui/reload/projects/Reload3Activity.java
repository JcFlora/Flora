package com.jc.flora.apps.ui.reload.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.reload.widget.AutoSwipeRefreshLayout;

/**
 * Created by Samurai on 2018/4/24.
 */
public class Reload3Activity extends AppCompatActivity {

    /** 下拉刷新布局 */
    private AutoSwipeRefreshLayout mSrlContent;

    /** 确认退出等待时间 */
    private static final long EXIT_WAIT_TIME = 2000;
    /** 第一次按返回的时间点 */
    private long mExitTime = 0;
    /** 刷新数据的任务 */
    private Runnable mStopReloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("实现点击back刷新");
        setContentView(R.layout.activity_reload3);
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
                ToastDelegate.show(Reload3Activity.this, "刷新数据");
            }
        };
        mSrlContent.postDelayed(mStopReloadTask, 1500);
    }

    public void reload(View v){
//        mSrlContent.autoRefresh();
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - mExitTime) > EXIT_WAIT_TIME){
            ToastDelegate.show(this, "再按一次返回键退出");
            mExitTime = System.currentTimeMillis();
            mSrlContent.autoRefresh();
        } else {
            ToastDelegate.cancel();
            ToastDelegate.onAppExit();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mSrlContent.removeCallbacks(mStopReloadTask);
        super.onDestroy();
    }

}