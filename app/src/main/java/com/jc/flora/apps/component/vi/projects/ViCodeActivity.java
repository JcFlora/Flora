package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/6/7.
 */
public class ViCodeActivity extends AppCompatActivity {

    private View mV1, mV2, mV3, mV4;
    private int mScreenWidth, mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用代码计算适配");
        setContentView(R.layout.activity_vi_code);
        findViews();
        getScreenWidthHeight();
        initViews();
    }

    private void findViews() {
        mV1 = findViewById(R.id.btn1);
        mV2 = findViewById(R.id.btn2);
        mV3 = findViewById(R.id.btn3);
        mV4 = findViewById(R.id.btn4);
    }

    private void getScreenWidthHeight() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    private void initViews() {
        // 第一个按钮，宽度100%，高度10%
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (mScreenHeight * 0.1f + 0.5f));
        mV1.setLayoutParams(params1);
        // 第二个按钮，宽度100%，高度30%
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (mScreenHeight * 0.3f + 0.5f));
        mV2.setLayoutParams(params2);
        // 第三个按钮，宽度50%，高度20%
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                (int) (mScreenWidth * 0.5f + 0.5f),
                (int) (mScreenHeight * 0.2f + 0.5f));
        mV3.setLayoutParams(params3);
        // 第四个按钮，宽度70%，高度填满剩下的空间
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                (int) (mScreenWidth * 0.7f + 0.5f),
                LinearLayout.LayoutParams.MATCH_PARENT);
        mV4.setLayoutParams(params4);
    }

}