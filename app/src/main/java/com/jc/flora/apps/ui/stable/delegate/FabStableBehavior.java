package com.jc.flora.apps.ui.stable.delegate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * Created by Samurai on 2017/6/10.
 */
public class FabStableBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    // 控件距离coordinatorLayout底部距离
    private float mViewY;
    // 动画是否在进行
    private boolean mIsAnimate;

    public FabStableBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 在嵌套滑动开始前回调
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && mViewY == 0) {
            // 获取控件距离父布局（coordinatorLayout）底部距离
            mViewY = coordinatorLayout.getHeight() - child.getY();
        }
        // 判断是否竖直滚动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //在嵌套滑动进行时，对象消费滚动距离前回调
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        // support25版本的bug，不能使用View.GONE来隐藏child，否则就不会继续触发两个回调方法
        // dy大于0是向上滚动 小于0是向下滚动
        if (dy >= 0 && !mIsAnimate && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dy < 0 && !mIsAnimate && child.getVisibility() == View.INVISIBLE) {
            show(child);
        }
    }

    // 隐藏时的动画
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(mViewY).setInterpolator(INTERPOLATOR).setDuration(300);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.INVISIBLE);
                mIsAnimate = false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }
        });
        animator.start();
    }

    // 显示时的动画
    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(300);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                mIsAnimate = false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }
        });
        animator.start();
    }
}