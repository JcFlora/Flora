package com.jc.flora.apps.scene.search.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by shijincheng on 2020/11/19.
 */
public class Search3ResultActivity extends AppCompatActivity {

    private View mBtnBack;
    private TextView mTvSearch;
    private RecyclerView mRvContent;
    private String mSearchWord;

    /**
     * 跳转到搜索结果页
     * @param context
     * @param searchWord
     */
    public static void gotoSearchResult(Context context, String searchWord) {
        Intent intent = new Intent(context, Search3ResultActivity.class);
        intent.putExtra("searchWord", searchWord);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索结果页");
        setContentView(R.layout.activity_search3_result);
        initViews();
        loadData();
    }

    private void initViews() {
        mBtnBack = findViewById(R.id.btn_back);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mRvContent = (RecyclerView) findViewById(R.id.rv);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));

        mSearchWord = getIntent().getStringExtra("searchWord");
        mTvSearch.setText(mSearchWord);
    }

    private void loadData(){
        ProjectsAdapter mAdapter = new ProjectsAdapter(Search3ResultActivity.this, getData(mSearchWord));
        mRvContent.setAdapter(mAdapter);
    }

    private ArrayList<Project> getData(String searchWord) {
        ArrayList<Project> list = new ArrayList<>();
        Project p = new Project();
        p.projectName = searchWord + "搜索数据";
        p.targetActivity = NotFoundActivity.class;
        for (int i = 0; i < 20; i++) {
            list.add(p);
        }
        return list;
    }

}