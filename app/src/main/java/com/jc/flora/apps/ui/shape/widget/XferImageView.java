package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Samurai on 2017/8/3.
 */
public class XferImageView extends ImageView {

    private float mCornerRadius;
    private Paint mContentPaint, mBorderPaint;
    private Xfermode mXferMode;
    private Bitmap mContentBitmap, mMaskBitmap;
    private int mBorderColor = Color.BLACK;
    private float mBorderWidth;
    private RectF mRectFBorder;
    private Shape mShape = Shape.OVAL;

    public XferImageView(Context context) {
        super(context);
        init();
    }

    public XferImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XferImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContentPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mXferMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    public void setShape(Shape shape) {
        mShape = shape;
        mMaskBitmap = null;
        mContentBitmap = null;
        invalidate();
    }

    public void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
        mShape = Shape.CORNER;
        mMaskBitmap = null;
        mContentBitmap = null;
        invalidate();
    }

    public void setBorder(@ColorInt int color, float width){
        mBorderColor = color;
        mBorderWidth = width;
        mMaskBitmap = null;
        mContentBitmap = null;
        mRectFBorder = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawContent(canvas);
        drawBorder(canvas);
    }

    private void drawContent(Canvas canvas){
        //获取盖在src上面的椭圆Bitmap
        initMaskBitmap();
        //两张图片以XferMode（DST_IN）的方式组合
        combineBitmap();
        //将最终的bitmap画到画板上面
        canvas.drawBitmap(mContentBitmap, 0, 0, mContentPaint);
    }

    //生成一个椭圆Bitmap,这个Bitmap宽高要与当前的View的宽高相同
    private void initMaskBitmap() {
        if (mMaskBitmap != null) {
            return;
        }
        mMaskBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        mContentPaint.reset();
        mContentPaint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        if (mShape == Shape.OVAL) {
            canvas.drawOval(rect, mContentPaint);
        } else {
            float offset = mBorderWidth / 2;
            canvas.drawRoundRect(rect, mCornerRadius + offset, mCornerRadius + offset, mContentPaint);
        }
    }

    //将两张图片以XferMode（DST_IN）的方式组合到一张照片中
    private void combineBitmap() {
        if(mContentBitmap != null){
            return;
        }
        //获取设置的src图片
        Drawable drawable = getDrawable();
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // 将drawable转bitmap
        mContentBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mContentBitmap);
//        drawable.setBounds(20,20,width-20,height-20);
        drawable.draw(canvas);
        // 先将XferMode设置好，然后将盖在上面的bitmap绘制出来
        mContentPaint.reset();
        mContentPaint.setXfermode(mXferMode);
        canvas.drawBitmap(mMaskBitmap, 0, 0, mContentPaint);
        mContentPaint.setXfermode(null);
    }

    private void drawBorder(Canvas canvas){
        if (mBorderWidth <= 0.01) {
            return;
        }
        if(mRectFBorder == null){
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
            float offset = mBorderWidth / 2;
            mRectFBorder = new RectF(offset, offset, getWidth() - offset, getHeight() - offset);
        }
        canvas.save();
        if (mShape == Shape.OVAL) {
            canvas.drawOval(mRectFBorder, mBorderPaint);
        }else{
            canvas.drawRoundRect(mRectFBorder, mCornerRadius, mCornerRadius, mBorderPaint);
        }
        canvas.restore();
    }

    public enum Shape{CORNER,OVAL}

}