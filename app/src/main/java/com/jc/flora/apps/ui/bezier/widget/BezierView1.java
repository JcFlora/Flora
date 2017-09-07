package com.jc.flora.apps.ui.bezier.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Samurai on 2017/9/7.
 */
public class BezierView1 extends View{

    private Paint mPaintBezier;
    private Path mPath = new Path();

    public BezierView1(Context context) {
        super(context);
        init();
    }

    public BezierView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 2阶贝塞尔曲线
        drawQuadBezier(canvas);
        // 保存画布
        canvas.save();
        // 3阶贝塞尔曲线
        drawCubicBezier(canvas);
        // 合并画布图层
        canvas.restore();
    }

    /**
     * 2阶贝塞尔曲线
     * @param canvas
     */
    private void drawQuadBezier(Canvas canvas){
        mPath.reset();
        mPath.moveTo(240, 240);
        mPath.quadTo(240, 360, 720, 360);
        canvas.drawPath(mPath, mPaintBezier);
    }

    /**
     * 3阶贝塞尔曲线
     * @param canvas
     */
    private void drawCubicBezier(Canvas canvas){
        mPath.reset();
        mPath.moveTo(240, 600);
        mPath.cubicTo(240, 960, 480, 840, 720, 960);
        canvas.drawPath(mPath, mPaintBezier);
    }

}