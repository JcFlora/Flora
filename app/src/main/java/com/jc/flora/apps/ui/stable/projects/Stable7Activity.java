package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/6/10.
 */
public class Stable7Activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable7);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
    }

    private void initViews() {
        mToolbar.setTitle("浮动标题（使用AppBarLayout）");
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