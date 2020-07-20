package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/8.
 */
public class ShapeGradientActivity extends AppCompatActivity {

    private AppCompatButton mBtnGradient, mBtnGradient2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("渐变背景");
        setContentView(R.layout.activity_shape_gradient);
        findViews();
        initViews();
    }

    private void findViews() {
        mBtnGradient = (AppCompatButton) findViewById(R.id.btn_gradient);
        mBtnGradient2 = (AppCompatButton) findViewById(R.id.btn_gradient2);
    }

    private void initViews() {
        mBtnGradient.setBackground(ShapeDelegate.getGradientDrawable(new int[]{0xfffdc901, 0xfffd9803, 0xfffd7601, 0xfffd4b02}));
        mBtnGradient2.setBackground(ShapeDelegate.getGradientCornerDrawable(60, new int[]{0xfffdc901, 0xfffd9803, 0xfffd7601, 0xfffd4b02}));
    }

}