package com.jc.flora.apps.ui.buoy.delegate;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Samurai on 2017/7/4.
 */
public class BuoyDelegate2 {

    public static void setFollowing(View v, final boolean need,
                                    final View.OnClickListener listener) {
        if (v == null) {
            return;
        }
        v.setOnTouchListener(new View.OnTouchListener() {
            private int mLastX, mLastY, mDownX, mDownY, mUpX, mUpY;
            private long mDownTimeMillion, mUpTimeMillion;
            int l, t, r, b;
            private int mWidth, mHeight;
            private int mScreenWidth, mScreenHeight, mDisplayHeight;

            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (mScreenWidth == 0) {
                    DisplayMetrics dm = v.getContext().getResources()
                            .getDisplayMetrics();
                    mScreenWidth = dm.widthPixels;
                    mScreenHeight = dm.heightPixels;
                }
                if (mDisplayHeight == 0) {
                    Rect frame = new Rect();
                    ((View) v.getParent()).getGlobalVisibleRect(frame);
                    mDisplayHeight = mScreenHeight - frame.top;
                }
                if (mWidth == 0) {
                    mWidth = v.getWidth();
                }
                if (mHeight == 0) {
                    mHeight = v.getHeight();
                }
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mLastX = rawX;
                        mLastY = rawY;
                        mDownX = (int) event.getRawX();
                        mDownY = (int) event.getRawY();
                        mDownTimeMillion = System.currentTimeMillis();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        int offsetX = rawX - mLastX;
                        int offsetY = rawY - mLastY;
                        l = v.getLeft() + offsetX;
                        t = v.getTop() + offsetY;
                        r = v.getRight() + offsetX;
                        b = v.getBottom() + offsetY;
                        if (l < 0) {
                            l = 0;
                            r = mWidth;
                        }
                        if (r > mScreenWidth) {
                            r = mScreenWidth;
                            l = mScreenWidth - mWidth;
                        }
                        if (t < 0) {
                            t = 0;
                            b = mHeight;
                        }
                        if (b > mDisplayHeight) {
                            b = mDisplayHeight;
                            t = mDisplayHeight - mHeight;
                        }
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                        params.leftMargin = l;
                        params.topMargin = t;
                        v.setLayoutParams(params);
                        mLastX = rawX;
                        mLastY = rawY;
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        mUpX = (int) event.getRawX();
                        mUpY = (int) event.getRawY();
                        if (need) {
                            if ((l + v.getWidth() / 2) < mScreenWidth / 2) {
                                l = 0;
                                r = l + v.getWidth();
                            } else {
                                r = mScreenWidth;
                                l = r - v.getWidth();
                            }
                            final TranslateAnimation animation = new TranslateAnimation(v, l, t);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            v.clearAnimation();
                                        }
                                    });
                            animation.setDuration(260);
                            v.startAnimation(animation);
                        }
                        mUpTimeMillion = System.currentTimeMillis();
                        if (listener != null && mUpTimeMillion - mDownTimeMillion < 1000
                                && Math.abs(mDownX - mUpX) < 3 && Math.abs(mDownY - mUpY) < 3) {
                            listener.onClick(v);
                        }
                        break;
                }
                return true;
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * View的位移动画
     */
    private static class TranslateAnimation extends Animation {
        /** 目标View */
        private View targetView;

        /**
         * 原始Left
         **/
        private int originalLeft;
        /**
         * 原始Top
         **/
        private int originalTop;
        /**
         * 目标Left
         **/
        private int targetLeft;
        /**
         * 目标Top
         **/
        private int targetTop;
        /**
         * 水平方向的距离
         **/
        private int distanceX;
        /**
         * 垂直方向的距离
         **/
        private int distanceY;

        /**
         * 构造方法
         *
         * @param view 被移动的View, 该View的父View必须是FrameLayout,不然会出错
         * @param left 目标left
         * @param top  目标top
         */
        public TranslateAnimation(View view, int left, int top) {
            setInterpolator(view.getContext(),
                    android.R.anim.accelerate_decelerate_interpolator);

            targetView = view;

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) targetView.getLayoutParams();
            originalLeft = lp.leftMargin;
            originalTop = lp.topMargin;

            targetLeft = left;
            targetTop = top;

            distanceX = targetLeft - originalLeft;
            distanceY = targetTop - originalTop;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (interpolatedTime == 0f && !hasStarted()) {
                // 确保初始化位置正确
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) targetView.getLayoutParams();
                lp.leftMargin = originalLeft;
                lp.topMargin = originalTop;
            } else if (interpolatedTime >= 1f && hasEnded()) {
                // 确保目标位置正确
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) targetView.getLayoutParams();
                lp.leftMargin = targetLeft;
                lp.topMargin = targetTop;
            } else {
                int delX = (int) ((float) distanceX * interpolatedTime);
                int delY = (int) ((float) distanceY * interpolatedTime);
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) targetView.getLayoutParams();
                // 根据时间计算出位移量
                lp.leftMargin = originalLeft + delX;
                lp.topMargin = originalTop + delY;
            }
            // 重新布局
            targetView.requestLayout();
        }
    }

}
