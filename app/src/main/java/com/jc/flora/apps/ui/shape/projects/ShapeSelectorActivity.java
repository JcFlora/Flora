package com.jc.flora.apps.ui.shape.projects;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/25.
 */
public class ShapeSelectorActivity extends AppCompatActivity {

    private AppCompatButton mBtnSelector;
    private AppCompatTextView mTvColorSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("高亮反馈");
        setContentView(R.layout.activity_shape_selector);
        findViews();
        initViews();
    }

    private void findViews() {
        mBtnSelector = (AppCompatButton) findViewById(R.id.btn_selector);
        mTvColorSelector = (AppCompatTextView) findViewById(R.id.tv_color_selector);
    }

    private void initViews() {
        mTvColorSelector.setTextColor(ShapeDelegate.getColorStateList(0xff666666, 0xffaa66cc));

        Drawable drawable = ShapeDelegate.getGradientCornerDrawable(60, new int[]{0xff4f8cfd, 0xff33b5e5, 0xff37bafd});
        Drawable pressedDrawable = ShapeDelegate.getGradientCornerDrawable(60, new int[]{0xfffdc901, 0xfffd9803, 0xfffd7601, 0xfffd4b02});
        mBtnSelector.setBackground(ShapeDelegate.getPressedSelectorDrawable(drawable, pressedDrawable));
    }

}