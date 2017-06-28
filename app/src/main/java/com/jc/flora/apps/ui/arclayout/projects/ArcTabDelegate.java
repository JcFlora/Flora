package com.jc.flora.apps.ui.arclayout.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.jc.flora.R;
import com.ogaclejapan.arclayout.ArcLayout;

/**
 * 弧形Tab业务管理
 * Created by shijincheng on 2017/2/9.
 */
public class ArcTabDelegate {

    /** Tab数量 */
    private static final int TAB_COUNT = 4;

    /** 指针旋转动画的单位角度（旋转1格的角度） */
    private static final float ARROW_ROTATION_ANIM_ANGLE_UNIT = 40f;
    /** 指针旋转动画的单位时间（旋转1格的时长） */
    private static final long ARROW_ROTATION_ANIM_DURATION_UNIT = 30;

    /** 锁定动画的中间状态缩放比例 */
    private static final float LOCK_ANIM_MIDDLE_SCALE = 1.5f;
    /** 锁定动画的结束状态缩放比例 */
    private static final float LOCK_ANIM_END_SCALE = 1.2f;
    /** 锁定动画的时长 */
    private static final long LOCK_ANIM_DURATION_UNIT = 500;
    /** Fling状态锁定周期 */
    private static final long FLING_STATUS_FIXED_INTERVAL = 500;

    /** 弧形布局控件 */
    private ArcLayout mLayoutArc;
    /** 指针 */
    private View mVArrow;
    /** 当前指向索引 */
    private int mPointIndex = 0;
    /** 当前指向角度 */
    private float mPointRotation = 30f;

    /** 标记量：是否在Fling状态 */
    private boolean mIsFling = false;
    /** 标记量：是否是锁定状态 */
    private boolean mIsLock = false;

    /** 弧形Tab监听器 */
    private OnArcTabSelectedListener mOnArcTabSelectedListener;

    /** 清除Fling状态消息标志 */
    private static final int MSG_CLEAR_FLING_STATUS = 0;

    public ArcTabDelegate(ArcLayout layoutArc, View vArrow){
        mLayoutArc = layoutArc;
        mVArrow = vArrow;
    }

    /**
     * 设置弧形Tab监听器
     * @param onArcTabSelectedListener
     */
    public void setOnArcTabSelectedListener(OnArcTabSelectedListener onArcTabSelectedListener) {
        mOnArcTabSelectedListener = onArcTabSelectedListener;
    }

    /** 初始化：设置初始显示状态和TabItem的点击事件 */
    public void init(){
        mVArrow.setRotation(mPointRotation);
        for (int i = 0, count = mLayoutArc.getChildCount(); i < count; i++) {
            final int j = i;
            mLayoutArc.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIsLock)
                        return;
                    flingAt(j);
//                    lockAt(j);
                }
            });
        }
        flingAt(0);
        lockAt(0);
    }

    /**
     * 在某位置滑过
     * @param index
     */
    public void flingAt(int index) {
        if (!mIsLock && index >= 0 && index < TAB_COUNT) {
            startFlingAnim(index);
            setFlingStatus();
        }
    }

    /**
     * 在某位置锁定
     * @param index
     */
    private void lockAt(int index) {
        mIsLock = true;
        resetScale();
        startLockAnim(index);
    }

    /** 锁定 */
    public void onLock() {
        if (!mIsLock && !mIsFling && mPointIndex >= 0 && mPointIndex < TAB_COUNT) {
            lockAt(mPointIndex);
        }
    }

    /** 解锁 */
    public void onUnlock() {
        mIsLock = false;
        flingAt(mPointIndex);
    }

    /**
     * 开始滑动动画
     * @param index
     */
    private void startFlingAnim(final int index){
        final int step = index - mPointIndex;
        final float startRotation = mPointRotation;
        final float endRotation = mPointRotation + step * ARROW_ROTATION_ANIM_ANGLE_UNIT;
        ObjectAnimator anim = ObjectAnimator.ofFloat(mVArrow, "rotation", startRotation, endRotation);
        anim.setDuration(ARROW_ROTATION_ANIM_DURATION_UNIT * Math.abs(step));
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mPointRotation = endRotation;
                mPointIndex += step;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setColor();
                if(mOnArcTabSelectedListener != null){
                    mOnArcTabSelectedListener.onFling(mPointIndex);
                }
            }
        });
        anim.start();
    }

    /** 设置Fling状态 */
    private void setFlingStatus(){
        mIsFling = true;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(MSG_CLEAR_FLING_STATUS, FLING_STATUS_FIXED_INTERVAL);
    }

    /** 设置标签的颜色 */
    private void setColor(){
        for (int i = 0, count = mLayoutArc.getChildCount(); i < count; i++) {
            int color = i == mPointIndex ? R.drawable.light_red_oval_selector : R.drawable.light_blue_oval_selector;
            mLayoutArc.getChildAt(i).setBackgroundResource(color);
        }
    }

    /** 缩放复位 */
    private void resetScale(){
        for (int i = 0, count = mLayoutArc.getChildCount(); i < count; i++) {
            mLayoutArc.getChildAt(i).setScaleX(1f);
            mLayoutArc.getChildAt(i).setScaleY(1f);
        }
    }

    /** 开始锁定动画 */
    private void startLockAnim(final int index) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, LOCK_ANIM_MIDDLE_SCALE, LOCK_ANIM_END_SCALE);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, LOCK_ANIM_MIDDLE_SCALE, LOCK_ANIM_END_SCALE);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(mLayoutArc.getChildAt(index), pvhX, pvhY);
        anim.setDuration(LOCK_ANIM_DURATION_UNIT);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mOnArcTabSelectedListener != null) {
                    mOnArcTabSelectedListener.onLocked(index);
                }
            }
        });
        anim.start();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CLEAR_FLING_STATUS:
                    clearFlingStatus();
                    break;
            }
        }
    };

    /** 清除Fling状态 */
    private void clearFlingStatus(){
        mIsFling = false;
    }


    public void onDestroy(){
        mHandler.removeCallbacksAndMessages(null);
    }

    /** 滑到上一个 */
    public void flingPre() {
        flingAt(mPointIndex - 1);
    }

    /** 滑到下一个 */
    public void flingNext() {
        flingAt(mPointIndex + 1);
    }

    /** 弧形Tab监听器 */
    public interface  OnArcTabSelectedListener {
        /**
         * 锁定时的回调
         * @param position
         */
        void onLocked(int position);

        /**
         * Fling时的回调
         * @param position
         */
        void onFling(int position);
    }

}
