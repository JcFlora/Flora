package com.jc.flora.apps.scene.search.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.apps.scene.search.delegate.SearchBarDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by shijincheng on 2020/11/17.
 */
public class Search1Activity extends AppCompatActivity {

    private View mBtnBack;
    private EditText mEtSearch;
    private View mBtnSearchClear;
    private TextView mBtnSearch;
    private RecyclerView mRvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("单页面搜索");
        setContentView(R.layout.activity_search1);
        initViews();
        initSearchBarDelegate();
    }

    private void initViews(){
        mBtnBack = findViewById(R.id.btn_back);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mBtnSearchClear = findViewById(R.id.btn_search_clear);
        mBtnSearch = (TextView) findViewById(R.id.btn_search);
        mRvContent = (RecyclerView) findViewById(R.id.rv);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchBarDelegate(){
        SearchBarDelegate searchBarDelegate = new SearchBarDelegate();
        searchBarDelegate.setEtSearch(mEtSearch);
        searchBarDelegate.setBtnSearchClear(mBtnSearchClear);
        searchBarDelegate.setBtnSearch(mBtnSearch);
        searchBarDelegate.addToActivity(this, "searchBar");
        searchBarDelegate.init(new SearchBarDelegate.CallSearchRequestBridge() {
            @Override
            public void requestSearch(String searchWord) {
                // 执行搜索请求
                ToastDelegate.show(Search1Activity.this, "搜索" + searchWord);
                ProjectsAdapter mAdapter = new ProjectsAdapter(Search1Activity.this, getData(searchWord));
                mRvContent.setAdapter(mAdapter);
            }
        });
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