package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jc.flora.R;

/**
 * Created by Samurai on 2018/5/21.
 */
public class ViConstraint2Activity extends AppCompatActivity {

    private Button mBtnLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用约束布局适配间距");
        setContentView(R.layout.activity_vi_constraint2);
        mBtnLeft = (Button) findViewById(R.id.btn_left);
    }

    public void onSpreadClick(View v){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBtnLeft.getLayoutParams();
        params.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD;
        mBtnLeft.setLayoutParams(params);
    }

    public void onSpreadInsideClick(View v){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBtnLeft.getLayoutParams();
        params.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE;
        mBtnLeft.setLayoutParams(params);
    }

    public void onPackedClick(View v){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBtnLeft.getLayoutParams();
        params.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED;
        mBtnLeft.setLayoutParams(params);
    }

}