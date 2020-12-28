package com.jc.flora.apps.scene.search.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/11/23.
 */
public class Search5EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("主页面添加热搜词功能");
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
                startActivity(new Intent(Search5EntryActivity.this, Search5Activity.class));
            }
        });
    }

}