package com.jc.flora.apps.scene.search.delegate;

import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.jc.flora.R;
import com.jc.flora.apps.scene.search.widget.FlowLayout;
import com.jc.flora.apps.scene.search.widget.TagAdapter;
import com.jc.flora.apps.scene.search.widget.TagFlowLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryWordShowDelegate {

    private AppCompatActivity mActivity;
    private View mLayoutHistorySearch;
    private TagFlowLayout mHistoryFlowLayout;
//    private FlexboxLayout mHistoryFlowLayout;
    private List<String> mHistorySearchList;

//    private SearchBarDelegate.CallSearchRequestBridge mCallSearchRequestBridge;

    public HistoryWordShowDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setLayoutHistory(View layoutHistorySearch, TagFlowLayout historyFlowLayout) {
        mLayoutHistorySearch = layoutHistorySearch;
        mHistoryFlowLayout = historyFlowLayout;
    }

    public void init(final SearchBarDelegate.CallSearchRequestBridge callSearchRequestBridge) {
        refreshHistoryFlowLayoutData();
//        mCallSearchRequestBridge = callSearchRequestBridge;
        mHistoryFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                callSearchRequestBridge.requestSearch(mHistorySearchList.get(position));
                return true;
            }
        });
    }

    public void refreshHistoryFlowLayoutData() {
        mHistorySearchList = HistorySearchCacheDelegate.getHistorySearch();
        boolean hasData = mHistorySearchList != null && !mHistorySearchList.isEmpty();
        mLayoutHistorySearch.setVisibility(hasData ? View.VISIBLE : View.GONE);
//        mHistoryFlowLayout.removeAllViews();
//        for (int i = 0; i < mHistorySearchList.size(); i++) {
//            mHistoryFlowLayout.addView(getTagView(mHistoryFlowLayout, i, mHistorySearchList));
//        }
        mHistoryFlowLayout.setAdapter(new TagAdapter(mHistorySearchList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                return getTagView(parent, position, mHistorySearchList);
            }
        });
    }

    private View getTagView(FlowLayout parent, final int position, List<String> data) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_search_word, parent, false);
        TextView tvSearch = view.findViewById(R.id.tv_search);
        tvSearch.setText(data.get(position));
//        tvSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mCallSearchRequestBridge != null){
//                    mCallSearchRequestBridge.requestSearch(mHistorySearchList.get(position));
//                }
//            }
//        });
        return view;
    }

}