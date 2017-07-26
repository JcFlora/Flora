package com.jc.flora.apps.ui.shape.projects;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

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
        if(!ShapeDelegate.setRippleDrawable(mBtnRipple1, 0xff0099cc, colorDrawable)){
            // 使用兼容模式高亮反馈
            Drawable pressedDrawable = new ColorDrawable(0xff0099cc);
            mBtnRipple1.setBackground(ShapeDelegate.getPressedSelectorDrawable(colorDrawable, pressedDrawable));
        }

        // 代码实现圆角水波纹反馈
        Drawable cornerDrawable = ShapeDelegate.getSolidCornerDrawable(20, 0xff33b5e5);
        if(!ShapeDelegate.setRippleDrawable(mBtnRipple2, 0xff0099cc, cornerDrawable)){
            // 使用兼容模式高亮反馈
            Drawable pressedDrawable = ShapeDelegate.getSolidCornerDrawable(20, 0xff0099cc);
            mBtnRipple2.setBackground(ShapeDelegate.getPressedSelectorDrawable(cornerDrawable, pressedDrawable));
        }

        // 代码实现渐变水波纹反馈
        int[] colors = {0xfffdc901, 0xfffd9803, 0xfffd7601, 0xfffd4b02};
        Drawable gradientDrawable = ShapeDelegate.getGradientDrawable(colors);
        if(!ShapeDelegate.setRippleDrawable(mBtnRipple3, 0xfffdc901, gradientDrawable)){
            // 使用兼容模式高亮反馈
            Drawable pressedDrawable = new ColorDrawable(0xfffd7601);
            mBtnRipple3.setBackground(ShapeDelegate.getPressedSelectorDrawable(gradientDrawable, pressedDrawable));
        }

        //todo 这种方式有个bug，水波纹的边界没有圆角，还是用xml写吧
        // 代码实现圆角渐变水波纹反馈
        Drawable gradientCornerDrawable = ShapeDelegate.getGradientCornerDrawable(60, colors);
        if(!ShapeDelegate.setRippleDrawable(mBtnRipple4, 0xfffdc901, gradientCornerDrawable)){
            // 使用兼容模式高亮反馈
            Drawable pressedDrawable = ShapeDelegate.getSolidCornerDrawable(60, 0xfffd7601);
            mBtnRipple4.setBackground(ShapeDelegate.getPressedSelectorDrawable(gradientCornerDrawable, pressedDrawable));
        }

        // 代码实现无边界水波纹反馈
        if(!ShapeDelegate.setRippleDrawable(mTvRipple5, 0xff0099cc, null)){
            // 使用兼容模式高亮反馈
            Drawable pressedDrawable = new ColorDrawable(0xff0099cc);
            mTvRipple5.setBackground(ShapeDelegate.getPressedSelectorDrawable(null, pressedDrawable));
        }
    }

}