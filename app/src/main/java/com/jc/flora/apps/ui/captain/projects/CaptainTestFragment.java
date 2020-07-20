package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class CaptainTestFragment extends Fragment{

    private String mTitle;
    private TextView mTvTitle;

    public void setTitle(String title) {
        mTitle = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_captain_fragment, container, false);
        mTvTitle = (TextView) v.findViewById(R.id.tv_title);
        if(!TextUtils.isEmpty(mTitle)){
            mTvTitle.setText(mTitle);
        }
        return v;
    }

}
