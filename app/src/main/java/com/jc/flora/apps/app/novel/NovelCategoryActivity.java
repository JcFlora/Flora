package com.jc.flora.apps.app.novel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/6/24.
 */
public class NovelCategoryActivity extends AppCompatActivity {

    private static final String[] TITLES = {"第一因 解体迅速", "第二因 解体信条",
            "第三因 解体升降", "第四因 解体让渡",
            "第五因 解体守护", "第六因 解体出处",
            "第七因 解体肖像", "第八因 解体照应",
            "最终因 解体路线", "后记"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("解体诸因");
        setContentView(R.layout.activity_novel_category);
        initViews();
    }

    private void initViews(){
        ListView lv = (ListView) findViewById(R.id.lv_category);
        lv.setAdapter(new ArrayAdapter(this, R.layout.item_novel_category_list, R.id.title_tv, TITLES));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NovelCategoryActivity.this, NovelContentActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("title", TITLES[position]);
                startActivity(intent);
            }
        });
    }

}
