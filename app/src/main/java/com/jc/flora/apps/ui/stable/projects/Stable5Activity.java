package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable5Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRvContent;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable5);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
    }

    private void initViews() {
        mToolbar.setTitle("浮动标题（监听滑动）");
        mToolbar.setTitleTextColor(Color.WHITE);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvContent.setLayoutManager(mLayoutManager);
        ProjectsAdapter mAdapter = new ProjectsAdapter(this, getData());
        mRvContent.setAdapter(mAdapter);
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(-1)){
                    mToolbar.setVisibility(View.VISIBLE);
                    return;
                }
                if (dy > 1 && getScrollYDistance() > 200) {
                    mToolbar.setVisibility(View.GONE);
                }else if(dy < -1){
                    mToolbar.setVisibility(View.VISIBLE);
                }
            }
        });
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

    public int getScrollYDistance() {
        int position = mLayoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = mLayoutManager.findViewByPosition(position);
        int itemHeight = firstVisibleChildView.getHeight();
        return (position) * itemHeight - firstVisibleChildView.getTop();
    }

}