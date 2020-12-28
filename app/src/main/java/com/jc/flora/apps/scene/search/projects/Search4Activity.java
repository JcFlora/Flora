package com.jc.flora.apps.scene.search.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.jc.flora.R;
import com.jc.flora.apps.component.cache.delegate.Spreader;
import com.jc.flora.apps.scene.search.delegate.HistorySearchCacheDelegate;
import com.jc.flora.apps.scene.search.delegate.HistoryWordShowDelegate;
import com.jc.flora.apps.scene.search.delegate.SearchBarDelegate;
import com.jc.flora.apps.scene.search.widget.TagFlowLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/11/19.
 */
public class Search4Activity extends AppCompatActivity {

    private View mBtnBack;
    private EditText mEtSearch;
    private View mBtnSearchClear;
    private TextView mBtnSearch;

    private View mLayoutHistorySearch;
    private View mBtnClearHistory;
    private TagFlowLayout mLayoutHistoryWordList;
    private HistoryWordShowDelegate mHistoryWordShowDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spreader.init(this);
        setTitle("主页面添加历史词功能");
        setContentView(R.layout.activity_search4);
        initViews();
        initSearchBarDelegate();
        initHistoryWordShowDelegate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHistoryWordShowDelegate.refreshHistoryFlowLayoutData();
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

        mLayoutHistorySearch = findViewById(R.id.layout_history_search);
        mBtnClearHistory = findViewById(R.id.iv_delete);
        mLayoutHistoryWordList = findViewById(R.id.flow_history);
        mBtnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistorySearchCacheDelegate.clear();
                mHistoryWordShowDelegate.refreshHistoryFlowLayoutData();
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
                // 执行搜索操作
                startSearch(searchWord);
            }
        });
    }

    private void initHistoryWordShowDelegate() {
        mHistoryWordShowDelegate = new HistoryWordShowDelegate(this);
        mHistoryWordShowDelegate.setLayoutHistory(mLayoutHistorySearch, mLayoutHistoryWordList);
        mHistoryWordShowDelegate.init(new SearchBarDelegate.CallSearchRequestBridge() {
            @Override
            public void requestSearch(String searchWord) {
                // 内容填充到EditText
                mEtSearch.setText(searchWord);
                // 光标移动到末尾
                mEtSearch.setSelection(searchWord.length());
                // 执行搜索操作
                startSearch(searchWord);
            }
        });

    }

    /**
     * 执行搜索操作
     * @param searchWord
     */
    private void startSearch(String searchWord) {
        // 跳转到搜索结果页
        Search3ResultActivity.gotoSearchResult(this, searchWord);
        // 写入历史缓存
        HistorySearchCacheDelegate.writeHistorySearch(searchWord);
    }

}