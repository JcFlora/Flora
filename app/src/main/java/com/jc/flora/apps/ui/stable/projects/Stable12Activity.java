package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2019/10/12.
 */
public class Stable12Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable12);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
    }

    private void initViews() {
        mToolbar.setTitle("底部浮动浮窗（使用AppBarLayout）");
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

}