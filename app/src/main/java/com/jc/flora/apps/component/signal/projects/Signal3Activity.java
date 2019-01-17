package com.jc.flora.apps.component.signal.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2019/1/17.
 */
public class Signal3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通过回调实现Fragment的通信");
        setContentView(R.layout.activity_signal3);
        initRootFragment();
    }

    private void initRootFragment(){
        Signal3FirstFragment fragment = new Signal3FirstFragment();
        fragment.addToActivity(this, R.id.layout_fragment, "rootFragment");
    }

}