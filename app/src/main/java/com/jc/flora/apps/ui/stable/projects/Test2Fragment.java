package com.jc.flora.apps.ui.stable.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Test2Fragment extends Fragment{

    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_test_fragment, container, false);
        mToolbar = (Toolbar) v.findViewById(R.id.tb_title);
        mToolbar.setTitle("测试Fragment");
        mToolbar.setTitleTextColor(Color.WHITE);
        return v;
    }

    public void fitStatusBar(StableDelegate stableDelegate){
        stableDelegate.fitStatusBar(mToolbar);
    }

}
