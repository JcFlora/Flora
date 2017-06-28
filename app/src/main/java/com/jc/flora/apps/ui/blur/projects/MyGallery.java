package com.jc.flora.apps.ui.blur.projects;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

import com.jc.flora.R;

public class MyGallery extends Gallery {


    public MyGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStaticTransformationsEnabled(true);
    }

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStaticTransformationsEnabled(true);
    }

    public MyGallery(Context context) {
        super(context);
        setStaticTransformationsEnabled(true);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        return false;
    }

    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
                + getPaddingLeft();
    }

    private static int getCenterOfView(View view) {
        return view.getLeft() + (view.getWidth() >> 1);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final float offset = calculateOffsetOfCenter(child);
        getTransformationMatrix(child, offset);

        //child.setAlpha(1 - Math.abs(offset));

        final int saveCount = canvas.save();
        canvas.concat(mMatrix);
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(saveCount);
        return ret;
    }

    protected float calculateOffsetOfCenter(View view) {
        final int pCenter = getCenterOfCoverflow();
        final int cCenter = getCenterOfView(view);

        float offset = (cCenter - pCenter) / (pCenter * 1.0f);
        offset = Math.min(offset, 1.0f);
        offset = Math.max(offset, -1.0f);

        return offset;
    }

    void getTransformationMatrix(View child, float offset) {
        final int halfWidth = child.getLeft() + (child.getMeasuredWidth() >> 1);
        final int halfHeight = child.getMeasuredHeight() >> 1;

        mCamera.save();
        //mCamera.translate(-offset * 50, 0.0f , Math.abs(offset) * 200);

        View v2 = child.findViewById(R.id.v2);
        if (Math.abs(offset) < 0.4) {
            v2.setBackgroundDrawable(null);
        } else {
            MyGalleryAdapter adapter = (MyGalleryAdapter) getAdapter();
            int index = (Integer) child.getTag();
            v2.setBackgroundDrawable(adapter.mBlurDrawables[index]);
        }

        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(-halfWidth, -halfHeight);
        mMatrix.postTranslate(halfWidth, halfHeight);
    }
    Camera mCamera = new Camera();
    Matrix mMatrix = new Matrix();

}
