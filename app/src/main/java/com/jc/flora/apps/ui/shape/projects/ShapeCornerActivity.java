package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/8.
 */
public class ShapeCornerActivity extends AppCompatActivity {

    private AppCompatButton mBtnCorner, mBtnCornerCustom;
    private AppCompatEditText mEtCorner, mEtCornerDash, mEtCornerCustom;

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
        mBtnCornerCustom = (AppCompatButton) findViewById(R.id.btn_corner_custom);
        mEtCorner = (AppCompatEditText) findViewById(R.id.et_corner);
        mEtCornerDash = (AppCompatEditText) findViewById(R.id.et_corner_dash);
        mEtCornerCustom = (AppCompatEditText) findViewById(R.id.et_corner_custom);
    }

    private void initViews() {
        mBtnCorner.setBackground(ShapeDelegate.getSolidCornerDrawable(20, 0xffff4081));
        mBtnCornerCustom.setBackground(ShapeDelegate.getSolidCornerDrawable(20, 0xff61cec2));
        mEtCorner.setBackground(ShapeDelegate.getStrokeCornerDrawable(20, 1, 0xff999999));
        mEtCornerDash.setBackground(ShapeDelegate.getDashCornerDrawable(20, 1, 0xffff4081,20,5));
        mEtCornerCustom.setBackground(ShapeDelegate.getStrokeCornerDrawable(20, 2, 0xffcccccc, 0xfff1f1f1));
    }

}