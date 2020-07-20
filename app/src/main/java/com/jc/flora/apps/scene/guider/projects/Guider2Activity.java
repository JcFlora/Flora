package com.jc.flora.apps.scene.guider.projects;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.scene.guider.delegate.GuiderDelegate2;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by Samurai on 2017/5/30.
 */
public class Guider2Activity extends AppCompatActivity {

    private ViewPager mVpGuider;
    private View mBtnPass;
    private View mBtnStart;
    private LinearLayout mLayoutGuiderIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StableDelegate(this).hideStatusBar();
        setContentView(R.layout.activity_guider2);
        findViews();
        initDelegate();
    }

    private void findViews() {
        mVpGuider = (ViewPager) findViewById(R.id.vp_guider);
        mBtnPass = findViewById(R.id.btn_pass);
        mBtnStart = findViewById(R.id.btn_start);
        mLayoutGuiderIndicators = (LinearLayout) findViewById(R.id.layout_guider_indicators);
    }

    private void initDelegate(){
        GuiderDelegate2 delegate = new GuiderDelegate2();
        delegate.setActivity(this);
        delegate.setVpGuider(mVpGuider);
        delegate.setBtnPass(mBtnPass);
        delegate.setBtnStart(mBtnStart);
        delegate.setLayoutGuiderIndicators(mLayoutGuiderIndicators);
        delegate.init();
    }

}