package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

/**
 * Created by Samurai on 2017/8/7.
 */
public abstract class PackDelegate {

    protected Paint mContentPaint, mCornerPaint, mBorderPaint;
    protected Path mPathTopLeft, mPathTopRight, mPathBottomLeft, mPathBottomRight;
    protected int mBorderColor = Color.BLACK;
    protected float mBorderWidth;
    protected RectF mRectFContent, mRectFBorder;

    public void initPaint() {
        initContentPaint();
        initCornerPaint();
        initBorderPaint();
    }

    public void setBorder(@ColorInt int color, float width){
        mBorderColor = color;
        mBorderWidth = width;
        mRectFBorder = null;
    }

    public void onSizeChanged(){
        mRectFContent = null;
        mRectFBorder = null;
        mPathTopLeft = null;
        mPathTopRight = null;
        mPathBottomLeft = null;
        mPathBottomRight = null;
    }

    public void beforeDispatchDraw(Canvas canvas) {
        drawContent(canvas);
    }

    public void afterDispatchDraw(Canvas canvas) {
        clipCorners(canvas);
        drawBorder(canvas);
    }

    private void initContentPaint(){
        mContentPaint = new Paint();
        mContentPaint.setXfermode(null);
    }

    private void initCornerPaint(){
        mCornerPaint = new Paint();
        mCornerPaint.setColor(Color.WHITE);
        mCornerPaint.setAntiAlias(true);
        mCornerPaint.setStyle(Paint.Style.FILL);
        mCornerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    private void initBorderPaint(){
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    private void drawContent(Canvas canvas){
        if(mRectFContent == null){
            mRectFContent = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        canvas.saveLayer(mRectFContent, mContentPaint, Canvas.ALL_SAVE_FLAG);
    }

    private void clipCorners(Canvas canvas){
        if(!isAllowedClip()){
            return;
        }
        clipTopLeft(canvas);
        clipTopRight(canvas);
        clipBottomLeft(canvas);
        clipBottomRight(canvas);
        canvas.restore();
    }

    private void drawBorder(Canvas canvas){
        if (mBorderWidth <= 0.01) {
            return;
        }
        float offset = 0;
        if(mRectFBorder == null){
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
            offset = mBorderWidth / 2;
            mRectFBorder = new RectF(offset, offset, canvas.getWidth() - offset, canvas.getHeight() - offset);
        }
        canvas.save();
        drawShape(canvas, offset);
        canvas.restore();
    }

    private void clipTopLeft(Canvas canvas) {
        if (mPathTopLeft == null) {
            initTopLeftPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathTopLeft, mCornerPaint);
    }

    private void clipTopRight(Canvas canvas) {
        if (mPathTopRight == null) {
            initTopRightPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathTopRight, mCornerPaint);
    }

    private void clipBottomLeft(Canvas canvas) {
        if (mPathBottomLeft == null) {
            initBottomLeftPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathBottomLeft, mCornerPaint);
    }

    private void clipBottomRight(Canvas canvas) {
        if (mPathBottomRight == null) {
            initBottomRightPath(canvas.getWidth(), canvas.getHeight());
        }
        canvas.drawPath(mPathBottomRight, mCornerPaint);
    }

    protected boolean isAllowedClip(){
        return true;
    }

    protected abstract void drawShape(Canvas canvas, float offset);
    protected abstract void initTopLeftPath(int width,int height);
    protected abstract void initTopRightPath(int width,int height);
    protected abstract void initBottomLeftPath(int width,int height);
    protected abstract void initBottomRightPath(int width,int height);

}