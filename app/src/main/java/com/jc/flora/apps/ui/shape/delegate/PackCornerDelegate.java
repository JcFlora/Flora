package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by Samurai on 2017/8/1.
 */
public class PackCornerDelegate {

    private Paint mContentPaint;
    private Paint mCornerPaint;
    private float mRadius = 30;
    private Path mPathTopLeft, mPathTopRight, mPathBottomLeft, mPathBottomRight;

    public void initPaint() {
        mContentPaint = new Paint();
        mContentPaint.setXfermode(null);
        mCornerPaint = new Paint();
        mCornerPaint.setColor(Color.WHITE);
        mCornerPaint.setAntiAlias(true);
        mCornerPaint.setStyle(Paint.Style.FILL);
        mCornerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public void beforeDispatchDraw(Canvas canvas){
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mContentPaint, Canvas.ALL_SAVE_FLAG);
    }

    public void afterDispatchDraw(Canvas canvas) {
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

//    public void afterDispatchDraw(Canvas canvas, int width, int height) {
//        drawTopLeft(canvas);
//        drawTopRight(canvas, width);
//        drawBottomLeft(canvas, height);
//        drawBottomRight(canvas, width, height);
//        canvas.restore();
//    }

    public void setRadius(float radius) {
        mRadius = radius;
        mPathTopLeft = null;
        mPathTopRight = null;
        mPathBottomLeft = null;
        mPathBottomRight = null;
    }

    private void drawTopLeft(Canvas canvas) {
        if(mRadius <= 0){
            return;
        }
        if (mPathTopLeft == null) {
            initTopLeftPath();
        }
        canvas.drawPath(mPathTopLeft, mCornerPaint);
    }

    private void drawTopRight(Canvas canvas) {
        if (mRadius <= 0) {
            return;
        }
        if (mPathTopRight == null) {
            initTopRightPath(canvas.getWidth());
        }
        canvas.drawPath(mPathTopRight, mCornerPaint);
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mRadius <= 0) {
            return;
        }
        if (mPathBottomLeft == null) {
            initBottomLeftPath(canvas.getHeight());
        }
        canvas.drawPath(mPathBottomLeft, mCornerPaint);
    }

    private void drawBottomRight(Canvas canvas) {
        if (mRadius <= 0) {
            return;
        }
        if (mPathBottomRight == null) {
            initBottomRightPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathBottomRight, mCornerPaint);
    }

    private void initTopLeftPath() {
        mPathTopLeft = new Path();
        mPathTopLeft.moveTo(0, mRadius);
        mPathTopLeft.lineTo(0, 0);
        mPathTopLeft.lineTo(mRadius, 0);
        mPathTopLeft.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, -90);
        mPathTopLeft.close();
    }

    private void initTopRightPath(int width){
        mPathTopRight = new Path();
        mPathTopRight.moveTo(width - mRadius, 0);
        mPathTopRight.lineTo(width, 0);
        mPathTopRight.lineTo(width, mRadius);
        mPathTopRight.arcTo(new RectF(width - 2 * mRadius, 0, width, mRadius * 2), 0, -90);
        mPathTopRight.close();
    }

    private void initBottomLeftPath(int height){
        mPathBottomLeft = new Path();
        mPathBottomLeft.moveTo(0, height - mRadius);
        mPathBottomLeft.lineTo(0, height);
        mPathBottomLeft.lineTo(mRadius, height);
        mPathBottomLeft.arcTo(new RectF(0, height - 2 * mRadius, mRadius * 2, height), 90, 90);
        mPathBottomLeft.close();
    }

    private void initBottomRightPath(int width, int height) {
        mPathBottomRight = new Path();
        mPathBottomRight.moveTo(width - mRadius, height);
        mPathBottomRight.lineTo(width, height);
        mPathBottomRight.lineTo(width, height - mRadius);
        mPathBottomRight.arcTo(new RectF(width - 2 * mRadius, height - 2 * mRadius, width, height), 0, 90);
        mPathBottomRight.close();
    }

}