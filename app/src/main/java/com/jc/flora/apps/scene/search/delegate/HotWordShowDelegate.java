package com.jc.flora.apps.scene.search.delegate;

import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.search.widget.FlowLayout;
import com.jc.flora.apps.scene.search.widget.TagAdapter;
import com.jc.flora.apps.scene.search.widget.TagFlowLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HotWordShowDelegate {

    private AppCompatActivity mActivity;
    private View mLayoutHotSearch;
    private TagFlowLayout mHotFlowLayout;
    private List<String> mHotSearchList;

    public HotWordShowDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setLayoutHot(View layoutHotSearch, TagFlowLayout hotFlowLayout) {
        mLayoutHotSearch = layoutHotSearch;
        mHotFlowLayout = hotFlowLayout;
    }

    public void init(final SearchBarDelegate.CallSearchRequestBridge callSearchRequestBridge) {
        mHotFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                callSearchRequestBridge.requestSearch(mHotSearchList.get(position));
                return true;
            }
        });
    }

    public void showHotSearchListAfterGetData(List<String> hotSearchList) {
        mHotSearchList = hotSearchList;
        boolean hasData = mHotSearchList != null && !mHotSearchList.isEmpty();
        mLayoutHotSearch.setVisibility(hasData ? View.VISIBLE : View.GONE);
        mHotFlowLayout.setAdapter(new TagAdapter(mHotSearchList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                return getTagView(parent, position, mHotSearchList);
            }
        });
    }

    private View getTagView(FlowLayout parent, int position, List<String> data) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_search_word, parent, false);
        TextView tvSearch = view.findViewById(R.id.tv_search);
        tvSearch.setText(data.get(position));
        return view;
    }

}