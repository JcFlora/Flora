package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/19.
 */
public class ViWeight3Activity extends AppCompatActivity {

    private EditText mEt1,mEt2,mEt3,mEt4;
    private TextView mBtnVerCode;
    private AppCompatButton mBtn1, mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用Weight适配间距2");
        setContentView(R.layout.activity_vi_weight3);
        findViews();
        initViews();
    }

    private void findViews() {
        mEt1 = (EditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        mEt3 = (EditText) findViewById(R.id.et3);
        mEt4 = (EditText) findViewById(R.id.et4);
        mBtnVerCode = (TextView) findViewById(R.id.btn_ver_code);
        mBtn1 = (AppCompatButton) findViewById(R.id.btn1);
        mBtn2 = (AppCompatButton) findViewById(R.id.btn2);
    }

    private void initViews() {
        mEt1.setBackground(ShapeDelegate.getStrokeCornerDrawable(10, 2, 0xffcccccc, 0xfff1f1f1));
        mEt2.setBackground(ShapeDelegate.getStrokeCornerDrawable(10, 2, 0xffcccccc, 0xfff1f1f1));
        mEt3.setBackground(ShapeDelegate.getStrokeCornerDrawable(10, 2, 0xffcccccc, 0xfff1f1f1));
        mEt4.setBackground(ShapeDelegate.getStrokeCornerDrawable(10, 2, 0xffcccccc, 0xfff1f1f1));
        mBtnVerCode.setBackground(ShapeDelegate.getStrokeCornerDrawable(10, 2, 0xffcccccc));
        mBtn1.setBackground(ShapeDelegate.getSolidCornerDrawable(10, 0xff61cec2));
        mBtn2.setBackground(ShapeDelegate.getSolidCornerDrawable(10, 0xfff8c72e));
    }

}