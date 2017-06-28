package com.jc.flora.apps.scene.guider.projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.scene.guider.delegate.GuiderDelegate4;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by Samurai on 2017/5/31.
 */
public class Guider4Activity extends AppCompatActivity {

    private ViewPager mVpGuider;
    private View mBtnSkip;
    private View mBtnNext;
    private View mBtnFinish;
    private LinearLayout mLayoutGuiderIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StableDelegate(this).hideStatusBar();
        setContentView(R.layout.activity_guider4);
        findViews();
        initDelegate();
    }

    private void findViews() {
        mVpGuider = (ViewPager) findViewById(R.id.vp_guider);
        mBtnSkip = findViewById(R.id.btn_skip);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnFinish = findViewById(R.id.btn_finish);
        mLayoutGuiderIndicators = (LinearLayout) findViewById(R.id.layout_guider_indicators);
    }

    private void initDelegate(){
        GuiderDelegate4 delegate = new GuiderDelegate4();
        delegate.setActivity(this);
        delegate.setVpGuider(mVpGuider);
        delegate.setBtnSkip(mBtnSkip);
        delegate.setBtnNext(mBtnNext);
        delegate.setBtnFinish(mBtnFinish);
        delegate.setLayoutGuiderIndicators(mLayoutGuiderIndicators);
        delegate.init();
    }

}