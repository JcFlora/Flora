package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Canvas;
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
    protected void dispatchDraw(Canvas canvas) {
        mPackCornerDelegate.beforeDispatchDraw(canvas);
        super.dispatchDraw(canvas);
        mPackCornerDelegate.afterDispatchDraw(canvas);
//        mPackCornerDelegate.afterDispatchDraw(canvas, getWidth(), getHeight());
    }

    public void setRadius(float radius){
        mPackCornerDelegate.setRadius(radius);
        invalidate();
    }

}