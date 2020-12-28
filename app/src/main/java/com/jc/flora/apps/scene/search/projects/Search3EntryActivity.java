package com.jc.flora.apps.scene.search.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/11/19.
 */
public class Search3EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索主页面和搜索结果页分离");
        setContentView(R.layout.activity_search2_entry);
        initSearchEntry();
    }

    /**
     * 初始化搜索入口功能
     */
    private void initSearchEntry() {
        View btnBack = findViewById(R.id.btn_back);
        TextView btnSearch = findViewById(R.id.btn_search);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Search3EntryActivity.this, Search3Activity.class));
            }
        });
    }

}