package com.jc.flora.apps.scene.search.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.search.delegate.SearchBarDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/11/19.
 */
public class Search3Activity extends AppCompatActivity {

    private View mBtnBack;
    private EditText mEtSearch;
    private View mBtnSearchClear;
    private TextView mBtnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索主页面和搜索结果页分离");
        setContentView(R.layout.activity_search3);
        initViews();
        initSearchBarDelegate();
    }

    private void initViews(){
        mBtnBack = findViewById(R.id.btn_back);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mBtnSearchClear = findViewById(R.id.btn_search_clear);
        mBtnSearch = (TextView) findViewById(R.id.btn_search);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                Search3ResultActivity.gotoSearchResult(Search3Activity.this, searchWord);
            }
        });
    }

}