package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Test3Fragment extends Fragment{

    private Toolbar mToolbar;
    private NestedScrollView mSvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_test_fragment3, container, false);
        mToolbar = (Toolbar) v.findViewById(R.id.tb_title);
        mToolbar.setTitle("测试Fragment：顶部渐显");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setVisibility(View.GONE);
        mSvContent = (NestedScrollView) v.findViewById(R.id.sv_content);
        mSvContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 400){
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.setAlpha(1);
                }else if(scrollY >= 1 && scrollY <= 400){
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.setAlpha(scrollY / 400f);
                }else if (scrollY == 0){
                    mToolbar.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }

    public void fitStatusBar(StableDelegate stableDelegate){
        stableDelegate.fitStatusBar(mToolbar);
    }

}
