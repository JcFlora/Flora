package com.jc.flora.apps.ui.stable.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StableDelegate stableDelegate = new StableDelegate(this);
        stableDelegate.hideStatusBar();
        setContentView(R.layout.activity_stable1);
//        stableDelegate.fitStatusBar(findViewById(R.id.layout_root));
    }

}