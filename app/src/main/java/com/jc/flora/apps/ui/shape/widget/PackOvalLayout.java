package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jc.flora.apps.ui.shape.delegate.PackOvalDelegate;

/**
 * Created by Samurai on 2017/8/1.
 */
public class PackOvalLayout extends FrameLayout {

    private PackOvalDelegate mPackOvalDelegate = new PackOvalDelegate();

    public PackOvalLayout(Context context) {
        super(context);
        mPackOvalDelegate.initPaint();
    }

    public PackOvalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPackOvalDelegate.initPaint();
    }

    public PackOvalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPackOvalDelegate.initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPackOvalDelegate.onSizeChanged();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPackOvalDelegate.beforeDispatchDraw(canvas);
        super.dispatchDraw(canvas);
        mPackOvalDelegate.afterDispatchDraw(canvas);
    }

    public void setBorder(@ColorInt int color, float width){
        mPackOvalDelegate.setBorder(color, width);
        invalidate();
    }

}