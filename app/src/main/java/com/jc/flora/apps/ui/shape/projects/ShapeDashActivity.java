package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * 虚线要关硬件加速
 * Created by Samurai on 2017/7/10.
 */
public class ShapeDashActivity extends AppCompatActivity {

    private View mVDash,mVDash2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("虚线");
        setContentView(R.layout.activity_shape_dash);
        findViews();
        initViews();
    }

    private void findViews() {
        mVDash = findViewById(R.id.v_dash);
        mVDash2 = findViewById(R.id.v_dash2);
    }

    private void initViews() {
//        mVDash.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mVDash.setBackground(ShapeDelegate.getDashLineDrawable(0xffdadada, 20, 5));
        mVDash2.setBackground(ShapeDelegate.getDashLineDrawable(0xffdadada, 20, 5, 100, 0, 100, 0));
    }

}