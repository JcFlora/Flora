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
public class PackOvalDelegate {

    private Paint mContentPaint;
    private Paint mCornerPaint;
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

    public void beforeDispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mContentPaint, Canvas.ALL_SAVE_FLAG);
    }

    public void afterDispatchDraw(Canvas canvas) {
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        if (mPathTopLeft == null) {
            initTopLeftPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathTopLeft, mCornerPaint);
    }

    private void drawTopRight(Canvas canvas) {
        if (mPathTopRight == null) {
            initTopRightPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathTopRight, mCornerPaint);
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mPathBottomLeft == null) {
            initBottomLeftPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathBottomLeft, mCornerPaint);
    }

    private void drawBottomRight(Canvas canvas) {
        if (mPathBottomRight == null) {
            initBottomRightPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathBottomRight, mCornerPaint);
    }

    private void initTopLeftPath(int width, int height) {
        mPathTopLeft = new Path();
        mPathTopLeft.moveTo(0, height / 2);
        mPathTopLeft.lineTo(0, 0);
        mPathTopLeft.lineTo(width / 2, 0);
        mPathTopLeft.arcTo(new RectF(0, 0, width, height), -90, -90);
        mPathTopLeft.close();
    }

    private void initTopRightPath(int width, int height) {
        mPathTopRight = new Path();
        mPathTopRight.moveTo(width / 2, 0);
        mPathTopRight.lineTo(width, 0);
        mPathTopRight.lineTo(width, height / 2);
        mPathTopRight.arcTo(new RectF(0, 0, width, height), 0, -90);
        mPathTopRight.close();
    }

    private void initBottomLeftPath(int width, int height) {
        mPathBottomLeft = new Path();
        mPathBottomLeft.moveTo(0, height / 2);
        mPathBottomLeft.lineTo(0, height);
        mPathBottomLeft.lineTo(width / 2, height);
        mPathBottomLeft.arcTo(new RectF(0, 0, width, height), 90, 90);
        mPathBottomLeft.close();
    }

    private void initBottomRightPath(int width, int height) {
        mPathBottomRight = new Path();
        mPathBottomRight.moveTo(width / 2, height);
        mPathBottomRight.lineTo(width, height);
        mPathBottomRight.lineTo(width, height / 2);
        mPathBottomRight.arcTo(new RectF(0, 0, width, height), 0, 90);
        mPathBottomRight.close();
    }

}