package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Samurai on 2017/8/3.
 */
public class ShaderImageView extends ImageView {

    private Shape mShape = Shape.OVAL;
    private float mCornerRadius;
    private int mBorderColor = Color.BLACK;
    private float mBorderWidth;
    private Paint mContentPaint, mBorderPaint;
    private RectF mRectFContent, mRectFBorder;

    public ShaderImageView(Context context) {
        super(context);
        init();
    }

    public ShaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContentPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
    }

    public void setShape(Shape shape) {
        mShape = shape;
        mRectFContent = null;
        mRectFBorder = null;
        invalidate();
    }

    public void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
        mShape = Shape.CORNER;
        mRectFContent = null;
        mRectFBorder = null;
        invalidate();
    }

    public void setBorder(@ColorInt int color, float width){
        mBorderColor = color;
        mBorderWidth = width;
        mRectFContent = null;
        mRectFBorder = null;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectFContent = null;
        mRectFBorder = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawContent(canvas);
        drawBorder(canvas);
    }

    private void drawContent(Canvas canvas){
        if(mRectFContent == null){
            // 将Drawable转为Bitmap
            Bitmap bitmap = drawableToBitmap(getDrawable());
            // 通过Bitmap和指定x,y方向的平铺方式构造出BitmapShader对象
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // 将BitmapShader设置到当前的Paint对象中
            mContentPaint.setShader(shader);
            mRectFContent = new RectF(0, 0, getWidth(), getHeight());
        }
        if (mShape == Shape.OVAL) {
            canvas.drawOval(mRectFContent, mContentPaint);
        }else{
            float offset = mBorderWidth / 2;
            canvas.drawRoundRect(mRectFContent, mCornerRadius + offset, mCornerRadius + offset, mContentPaint);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
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