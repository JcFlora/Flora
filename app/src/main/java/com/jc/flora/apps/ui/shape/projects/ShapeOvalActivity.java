package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/27.
 */
public class ShapeOvalActivity extends AppCompatActivity {

    private AppCompatButton mBtnOval, mBtnRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("椭圆/圆形背景");
        setContentView(R.layout.activity_shape_oval);
        findViews();
        initViews();
    }

    private void findViews() {
        mBtnOval = (AppCompatButton) findViewById(R.id.btn_oval);
        mBtnRound = (AppCompatButton) findViewById(R.id.btn_round);
    }

    private void initViews() {
        mBtnOval.setBackground(ShapeDelegate.getOvalDrawable(0xffff4081));
        mBtnRound.setBackground(ShapeDelegate.getOvalDrawable(0xffff4081));
    }

}