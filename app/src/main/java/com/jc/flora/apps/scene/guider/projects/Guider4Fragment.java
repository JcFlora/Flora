package com.jc.flora.apps.scene.guider.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Samurai on 2017/5/31.
 */
public class Guider4Fragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static Guider4Fragment newInstance(int layoutResId) {
        Guider4Fragment guiderFragment = new Guider4Fragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        guiderFragment.setArguments(args);

        return guiderFragment;
    }

    private int layoutResId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

}