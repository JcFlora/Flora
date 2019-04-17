package com.jc.flora.apps.ui.progress.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by Samurai on 2019/4/17.
 */
public class RoundProgressBar extends View {

    /** 背景圆环的颜色 */
    private int mBgColor;
    /** 前景圆环进度的颜色 */
    private int mFgColor;
    /** 中间进度百分比的字符串的颜色 */
    private int mTextColor;
    /** 中间进度百分比的字符串的字体大小 */
    private float mTextSize;
    /** 圆环的宽度 */
    private float mStrokeWidth;
    /** 最大进度 */
    private int mMax;
    /** 当前进度 */
    private int mProgress;
    /** 是否显示中间的进度 */
    private boolean mShowText;
    /** 圆弧大小界限 */
    private RectF mOval;

    /** 背景圆环的画笔 */
    private Paint mBgPaint;
    /** 前景圆环的画笔 */
    private Paint mFgPaint;
    /** 文字的画笔 */
    private Paint mTextPaint;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * 获取自定义属性和默认值
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        mBgColor = typedArray.getColor(R.styleable.RoundProgressBar_bgColor, Color.GRAY);
        mFgColor = typedArray.getColor(R.styleable.RoundProgressBar_fgColor, Color.BLUE);
        mTextColor = typedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLUE);
        mTextSize = typedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        mStrokeWidth = typedArray.getDimension(R.styleable.RoundProgressBar_strokeWidth, 5);
        mMax = typedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        mShowText = typedArray.getBoolean(R.styleable.RoundProgressBar_showText, false);
        typedArray.recycle();
    }

    private void initPaint(){
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor); //设置背景圆环的颜色
        mBgPaint.setStyle(Paint.Style.STROKE); //设置空心
        mBgPaint.setStrokeWidth(mStrokeWidth); //设置圆环的宽度
        mBgPaint.setAntiAlias(true);  //消除锯齿

        mFgPaint = new Paint();
        mFgPaint.setColor(mFgColor);  //设置前景圆环的颜色
        mFgPaint.setStyle(Paint.Style.STROKE); //设置空心
        mFgPaint.setStrokeWidth(mStrokeWidth); //设置圆环的宽度
        mFgPaint.setAntiAlias(true);  //消除锯齿

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(Typeface.DEFAULT); //设置字体
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取圆心的x坐标
        int centre = getWidth()/2;
        // //圆环的半径
        int radius = (int) (centre - mStrokeWidth /2);
        if(mOval == null){
            mOval = new RectF(centre - radius, centre - radius, centre+ radius, centre + radius);
        }
        // 画背景圆环
        drawBgCircle(canvas, centre, radius);
        // 画前景进度圆弧
        drawFgArc(canvas);
        // 画进度百分比
        if(mShowText){
            drawCenterText(canvas, centre);
        }
    }

    /**
     * 画背景圆环
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawBgCircle(Canvas canvas, int centre, int radius) {
        //画出圆环
        canvas.drawCircle(centre, centre, radius, mBgPaint);
    }

    /**
     * 画前景进度圆弧
     * @param canvas
     */
    private void drawFgArc(Canvas canvas) {
        if(mProgress != 0){
            //根据进度画圆弧
            float sweepAngle = 360 * (float) mProgress / (float) mMax;
            canvas.drawArc(mOval, 270, sweepAngle, false, mFgPaint);
        }
    }


    /**
     * 画进度百分比
     * @param canvas
     * @param centre
     */
    private void drawCenterText(Canvas canvas, int centre) {
        //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        int percent = (int)(((float) mProgress / (float) mMax) * 100);
        if(percent >= 0){
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            int baseLineY = (int) (mOval.centerY() - fontMetrics.top/2 - fontMetrics.bottom/2);
            //画出进度百分比
            canvas.drawText(percent + "%", centre, baseLineY, mTextPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOval = null;
    }

    public synchronized int getMaxProgress() {
        return mMax;
    }

    /**
     * 设置进度的最大值
     * @param maxProgress
     */
    public synchronized void setMaxProgress(int maxProgress) {
        if(maxProgress < 0){
            maxProgress = 0;
        }
        mMax = maxProgress;
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    public synchronized int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if(progress < 0){
            progress = 0;
        }
        if(progress > mMax){
            progress = mMax;
        }
        mProgress = progress;
        postInvalidate();
    }


    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int bgColor) {
        mBgColor = bgColor;
        if(mBgPaint != null){
            mBgPaint.setColor(mBgColor);  //设置背景圆环的颜色
        }
        invalidate();
    }

    public int getFgColor() {
        return mFgColor;
    }

    public void setFgColor(int fgColor) {
        mFgColor = fgColor;
        if(mFgPaint != null){
            mFgPaint.setColor(mFgColor);  //设置前景圆环的颜色
        }
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        if(mTextPaint != null){
            mTextPaint.setColor(mTextColor);
        }
        invalidate();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        if(mTextPaint != null){
            mTextPaint.setTextSize(mTextSize);
        }
        invalidate();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        mOval = null;
        if(mBgPaint != null){
            mBgPaint.setStrokeWidth(mStrokeWidth); //设置圆环的宽度
        }
        if(mFgPaint != null){
            mFgPaint.setStrokeWidth(mStrokeWidth); //设置圆环的宽度
        }
        invalidate();
    }

}