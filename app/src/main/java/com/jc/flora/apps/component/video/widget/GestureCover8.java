package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by Samurai on 2019/3/25.
 */
public class GestureCover8 extends BaseVideoGestureCover{

    private int mWidth, mHeight;

    private boolean mFirstTouch;
    private boolean mGestureEnable = true;
    private boolean mHorizontalSlide;

    private OnSingleTapUpListener mOnSingleTapUpListener;

    private SeekGestureDelegate mSeekGestureDelegate;

    public GestureCover8(@NonNull Context context) {
        super(context);
        init();
    }

    public GestureCover8(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureCover8(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_video_gesture_cover8, this, true);
        View layoutSeekBox = findViewById(R.id.layout_seek_box);
        TextView tvStepTime = findViewById(R.id.tv_step_time);
        TextView tvProgressTime = findViewById(R.id.tv_progress_time);

        mSeekGestureDelegate = new SeekGestureDelegate();
        mSeekGestureDelegate.setLayoutSeekBox(layoutSeekBox);
        mSeekGestureDelegate.setTvStepTime(tvStepTime);
        mSeekGestureDelegate.setTvProgressTime(tvProgressTime);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getWidth();
                mHeight = getHeight();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setDuration(int duration) {
        mSeekGestureDelegate.setDuration(duration);
    }

    public void setCurrentPosition(int currentPosition) {
        mSeekGestureDelegate.setCurrentPosition(currentPosition);
    }

    public void setOnSingleTapUpListener(OnSingleTapUpListener l) {
        mOnSingleTapUpListener = l;
    }

    public void setOnSeekGestureListener(SeekGestureDelegate.OnSeekGestureListener l) {
        mSeekGestureDelegate.setOnSeekGestureListener(l);
    }

    public void setGestureEnable(boolean gestureEnable) {
        mGestureEnable = gestureEnable;
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        if(mOnSingleTapUpListener != null){
            mOnSingleTapUpListener.onSingleTapUp();
        }
    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {
        mFirstTouch = true;
        mSeekGestureDelegate.setHorizontalMove(false);
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(!mGestureEnable){
            return;
        }
        float mOldX = e1.getX();
        float deltaX = mOldX - e2.getX();
        if (mFirstTouch) {
            mFirstTouch = false;
            mHorizontalSlide = Math.abs(distanceX) >= Math.abs(distanceY);
        }
        if(mHorizontalSlide){
            mSeekGestureDelegate.onHorizontalSlide(- deltaX / mWidth);
        }
    }

    @Override
    public void onEndGesture() {
        mSeekGestureDelegate.onEndGesture();
    }

}