package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jc.flora.apps.ui.shape.delegate.PackCornerDelegate;

/**
 * Created by Samurai on 2017/8/1.
 */
public class PackCornerLayout extends FrameLayout {

    private PackCornerDelegate mPackCornerDelegate = new PackCornerDelegate();

    public PackCornerLayout(Context context) {
        super(context);
        mPackCornerDelegate.initPaint();
    }

    public PackCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPackCornerDelegate.initPaint();
    }

    public PackCornerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPackCornerDelegate.initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPackCornerDelegate.onSizeChanged();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPackCornerDelegate.beforeDispatchDraw(canvas);
        super.dispatchDraw(canvas);
        mPackCornerDelegate.afterDispatchDraw(canvas);
    }

    public void setRadius(float radius){
        mPackCornerDelegate.setRadius(radius);
        invalidate();
    }

    public void setBorder(@ColorInt int color, float width){
        mPackCornerDelegate.setBorder(color, width);
        invalidate();
    }

}