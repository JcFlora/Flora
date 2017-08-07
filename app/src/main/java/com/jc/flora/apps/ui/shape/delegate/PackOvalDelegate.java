package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by Samurai on 2017/8/1.
 */
public class PackOvalDelegate extends PackDelegate{

    @Override
    protected void drawShape(Canvas canvas, float offset) {
        canvas.drawOval(mRectFBorder, mBorderPaint);
    }

    @Override
    protected void initTopLeftPath(int width, int height) {
        mPathTopLeft = new Path();
        mPathTopLeft.moveTo(0, height / 2);
        mPathTopLeft.lineTo(0, 0);
        mPathTopLeft.lineTo(width / 2, 0);
        mPathTopLeft.arcTo(new RectF(0, 0, width, height), -90, -90);
        mPathTopLeft.close();
    }

    @Override
    protected void initTopRightPath(int width, int height) {
        mPathTopRight = new Path();
        mPathTopRight.moveTo(width / 2, 0);
        mPathTopRight.lineTo(width, 0);
        mPathTopRight.lineTo(width, height / 2);
        mPathTopRight.arcTo(new RectF(0, 0, width, height), 0, -90);
        mPathTopRight.close();
    }

    @Override
    protected void initBottomLeftPath(int width, int height) {
        mPathBottomLeft = new Path();
        mPathBottomLeft.moveTo(0, height / 2);
        mPathBottomLeft.lineTo(0, height);
        mPathBottomLeft.lineTo(width / 2, height);
        mPathBottomLeft.arcTo(new RectF(0, 0, width, height), 90, 90);
        mPathBottomLeft.close();
    }

    @Override
    protected void initBottomRightPath(int width, int height) {
        mPathBottomRight = new Path();
        mPathBottomRight.moveTo(width / 2, height);
        mPathBottomRight.lineTo(width, height);
        mPathBottomRight.lineTo(width, height / 2);
        mPathBottomRight.arcTo(new RectF(0, 0, width, height), 0, 90);
        mPathBottomRight.close();
    }

}