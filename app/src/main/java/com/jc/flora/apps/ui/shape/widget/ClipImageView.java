package com.jc.flora.apps.ui.shape.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Samurai on 2017/8/9.
 */
public class ClipImageView extends ImageView {

    private Shape mShape = Shape.OVAL;
    private float mCornerRadius;
    private int mBorderColor = Color.BLACK;
    private float mBorderWidth;
    private Paint mContentPaint, mBorderPaint;
    private RectF mRectFContent, mRectFBorder;
    private Bitmap mContentBitmap;
    private Path mClipPath;

    public ClipImageView(Context context) {
        super(context);
        init();
    }

    public ClipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContentPaint = new Paint();
        mContentPaint.setAntiAlias(true);
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
        if (mRectFContent == null) {
            // 将Drawable转为Bitmap
            mContentBitmap = drawableToBitmap(getDrawable());
            mRectFContent = new RectF(0, 0, getWidth(), getHeight());
            mClipPath = new Path();
            if (mShape == Shape.OVAL) {
                // 按照逆时针方向添加一个椭圆
                mClipPath.addOval(mRectFContent, Path.Direction.CCW);
            } else {
                float offset = mBorderWidth / 2;
                mClipPath.addRoundRect(mRectFContent, mCornerRadius + offset, mCornerRadius + offset, Path.Direction.CCW);
            }
        }
        //先将canvas保存
        canvas.save();
        //设置为在区域内绘制
        canvas.clipPath(mClipPath);
        //绘制Bitmap
        canvas.drawBitmap(mContentBitmap, 0, 0, mContentPaint);
        //恢复Canvas
        canvas.restore();
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