package com.jc.flora.apps.ui.bgchange.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.bgchange.delegate.BgChange1Delegate;

/**
 * Created by shijincheng on 2017/2/4.
 */
public class TestBgChange1Activity extends AppCompatActivity {

    private View mVContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgchange1);
        mVContent = findViewById(R.id.layout_content);
        BgChange1Delegate.getInstance();
        findViewById(R.id.btn_go).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BgChange1Delegate.getInstance().onResume(mVContent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BgChange1Delegate.getInstance().onPause();
    }

}