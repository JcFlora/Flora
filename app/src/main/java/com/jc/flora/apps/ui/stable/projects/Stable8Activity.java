package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/6/10.
 */
public class Stable8Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRvContent;
    /** 下拉刷新布局 */
    private SwipeRefreshLayout mSrlContent;
    /** 刷新数据的任务 */
    private Runnable mStopReloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable8);
        findViews();
        initViews();
        initReload();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
        mSrlContent = (SwipeRefreshLayout) findViewById(R.id.srl_content);
    }

    private void initViews() {
        mToolbar.setTitle("浮动标题、导航、FAB（平移动画）");
        mToolbar.setTitleTextColor(Color.WHITE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvContent.setLayoutManager(layoutManager);
        ProjectsAdapter mAdapter = new ProjectsAdapter(this, getData());
        mRvContent.setAdapter(mAdapter);
    }

    private ArrayList<Project> getData(){
        ArrayList<Project> list = new ArrayList<>();
        Project p = new Project();
        p.projectName = "测试数据";
        p.targetActivity = NotFoundActivity.class;
        for (int i = 0; i < 20; i++) {
            list.add(p);
        }
        return list;
    }

    private void initReload(){
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
                ToastDelegate.show(Stable8Activity.this,"刷新数据");
            }
        };
        mSrlContent.postDelayed(mStopReloadTask, 1500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSrlContent.removeCallbacks(mStopReloadTask);
    }

}