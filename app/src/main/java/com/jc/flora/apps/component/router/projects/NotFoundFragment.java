package com.jc.flora.apps.component.router.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2018/12/28.
 */

public class NotFoundFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView iv = new ImageView(getActivity());
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setImageResource(R.drawable.bg_not_found);
        return iv;
    }

}
