package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public class GestureCover9 extends BaseVideoGestureCover{

    private int mWidth, mHeight;

    private boolean mFirstTouch;
    private boolean mGestureEnable = true;
    private boolean mHorizontalSlide;
    private boolean mLeftVerticalSlide;

    private OnSingleTapUpListener mOnSingleTapUpListener;

    private SeekGestureDelegate mSeekGestureDelegate;
    private BrightnessGestureDelegate mBrightnessGestureDelegate;

    public GestureCover9(@NonNull Context context) {
        super(context);
        init();
    }

    public GestureCover9(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureCover9(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_video_gesture_cover9, this, true);
        initSeekGestureDelegate();
        initBrightnessGestureDelegate();
    }

    private void initSeekGestureDelegate(){
        View layoutSeekBox = findViewById(R.id.layout_seek_box);
        TextView tvStepTime = findViewById(R.id.tv_step_time);
        TextView tvProgressTime = findViewById(R.id.tv_progress_time);
        mSeekGestureDelegate = new SeekGestureDelegate();
        mSeekGestureDelegate.setLayoutSeekBox(layoutSeekBox);
        mSeekGestureDelegate.setTvStepTime(tvStepTime);
        mSeekGestureDelegate.setTvProgressTime(tvProgressTime);
    }

    private void initBrightnessGestureDelegate(){
        View layoutBrightnessBox = findViewById(R.id.layout_brightness_box);
        TextView tvBrightnessText = findViewById(R.id.tv_brightness_text);
        mBrightnessGestureDelegate = new BrightnessGestureDelegate();
        mBrightnessGestureDelegate.setLayoutBrightnessBox(layoutBrightnessBox);
        mBrightnessGestureDelegate.setTvBrightnessText(tvBrightnessText);
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
        float mOldX = e1.getX(), mOldY = e1.getY();
        float deltaX = mOldX - e2.getX();
        float deltaY = mOldY - e2.getY();
        if (mFirstTouch) {
            mFirstTouch = false;
            mHorizontalSlide = Math.abs(distanceX) >= Math.abs(distanceY);
            mLeftVerticalSlide = mOldX < mWidth * 0.5f;
        }
        if (mHorizontalSlide) {
            mSeekGestureDelegate.onHorizontalSlide(-deltaX / mWidth);
        } else {
            if (Math.abs(deltaY) > mHeight) {
                return;
            }
            mHorizontalSlide = false;
            mSeekGestureDelegate.setFastForwardState(false);
            if (mLeftVerticalSlide) {
                mBrightnessGestureDelegate.onLeftVerticalSlide(deltaY / mHeight);
            }
        }
    }

    @Override
    public void onEndGesture() {
        mSeekGestureDelegate.onEndGesture();
        mBrightnessGestureDelegate.onEndGesture();
    }

}