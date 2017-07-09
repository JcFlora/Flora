package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/8.
 */
public class ShapeCornerActivity extends AppCompatActivity {

    private AppCompatButton mBtnCorner;
    private AppCompatEditText mEtCorner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("圆角背景");
        setContentView(R.layout.activity_shape_corner);
        findViews();
        initViews();
    }

    private void findViews() {
        mBtnCorner = (AppCompatButton) findViewById(R.id.btn_corner);
        mEtCorner = (AppCompatEditText) findViewById(R.id.et_corner);
    }

    private void initViews() {
        mBtnCorner.setBackground(ShapeDelegate.getSolidCornerDrawable(20, 0xffff4081));
        mEtCorner.setBackground(ShapeDelegate.getStrokeCornerDrawable(20, 1, 0xff999999));
    }

}