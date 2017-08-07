package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by Samurai on 2017/8/1.
 */
public class PackCornerDelegate extends PackDelegate{

    private float mRadius = 0;

    public void setRadius(float radius) {
        mRadius = radius;
        mPathTopLeft = null;
        mPathTopRight = null;
        mPathBottomLeft = null;
        mPathBottomRight = null;
    }

    protected boolean isAllowedClip(){
        return mRadius > 0;
    }

    @Override
    protected void drawShape(Canvas canvas, float offset) {
        float r = mRadius - offset;
        if (r > 0) {
            canvas.drawRoundRect(mRectFBorder, r, r, mBorderPaint);
        } else {
            canvas.drawRect(mRectFBorder, mBorderPaint);
        }
    }

    @Override
    protected void initTopLeftPath(int width, int height) {
        mPathTopLeft = new Path();
        mPathTopLeft.moveTo(0, mRadius);
        mPathTopLeft.lineTo(0, 0);
        mPathTopLeft.lineTo(mRadius, 0);
        mPathTopLeft.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, -90);
        mPathTopLeft.close();
    }

    @Override
    protected void initTopRightPath(int width, int height){
        mPathTopRight = new Path();
        mPathTopRight.moveTo(width - mRadius, 0);
        mPathTopRight.lineTo(width, 0);
        mPathTopRight.lineTo(width, mRadius);
        mPathTopRight.arcTo(new RectF(width - 2 * mRadius, 0, width, mRadius * 2), 0, -90);
        mPathTopRight.close();
    }

    @Override
    protected void initBottomLeftPath(int width, int height){
        mPathBottomLeft = new Path();
        mPathBottomLeft.moveTo(0, height - mRadius);
        mPathBottomLeft.lineTo(0, height);
        mPathBottomLeft.lineTo(mRadius, height);
        mPathBottomLeft.arcTo(new RectF(0, height - 2 * mRadius, mRadius * 2, height), 90, 90);
        mPathBottomLeft.close();
    }

    @Override
    protected void initBottomRightPath(int width, int height) {
        mPathBottomRight = new Path();
        mPathBottomRight.moveTo(width - mRadius, height);
        mPathBottomRight.lineTo(width, height);
        mPathBottomRight.lineTo(width, height - mRadius);
        mPathBottomRight.arcTo(new RectF(width - 2 * mRadius, height - 2 * mRadius, width, height), 0, 90);
        mPathBottomRight.close();
    }

}