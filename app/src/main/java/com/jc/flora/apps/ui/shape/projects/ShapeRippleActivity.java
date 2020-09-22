package com.jc.flora.apps.ui.shape.projects;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

/**
 * Created by Samurai on 2017/7/23.
 */
public class ShapeRippleActivity extends AppCompatActivity {

    private AppCompatButton mBtnRipple1, mBtnRipple2, mBtnRipple3, mBtnRipple4;
    private AppCompatTextView mTvRipple5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("水波纹反馈(5.0以上有效)");
        setContentView(R.layout.activity_shape_ripple);
        findViews();
        initViews();
    }

    private void findViews() {
        mBtnRipple1 = (AppCompatButton) findViewById(R.id.btn_ripple1);
        mBtnRipple2 = (AppCompatButton) findViewById(R.id.btn_ripple2);
        mBtnRipple3 = (AppCompatButton) findViewById(R.id.btn_ripple3);
        mBtnRipple4 = (AppCompatButton) findViewById(R.id.btn_ripple4);
        mTvRipple5 = (AppCompatTextView) findViewById(R.id.tv_ripple5);
    }

    private void initViews() {
        // 代码实现水波纹反馈
        Drawable colorDrawable = new ColorDrawable(0xff33b5e5);
        ShapeDelegate.setRippleDrawable(mBtnRipple1, 0xff0099cc, colorDrawable);

        // 代码实现圆角水波纹反馈
        Drawable cornerDrawable = ShapeDelegate.getSolidCornerDrawable(20, 0xff33b5e5);
        ShapeDelegate.setRippleDrawable(mBtnRipple2, 0xff0099cc, cornerDrawable);

        // 代码实现渐变水波纹反馈
        int[] colors = {0xfffdc901, 0xfffd9803, 0xfffd7601, 0xfffd4b02};
        Drawable gradientDrawable = ShapeDelegate.getGradientDrawable(colors);
        ShapeDelegate.setRippleDrawable(mBtnRipple3, 0xfffdc901, gradientDrawable);

        //todo 这种方式有个bug，水波纹的边界没有圆角，还是用xml写吧
        // 代码实现圆角渐变水波纹反馈
        Drawable gradientCornerDrawable = ShapeDelegate.getGradientCornerDrawable(60, colors);
        ShapeDelegate.setRippleDrawable(mBtnRipple4, 0xfffdc901, gradientCornerDrawable);

        // 代码实现无边界水波纹反馈
        ShapeDelegate.setRippleDrawable(mTvRipple5, 0xff0099cc, null);
    }

}