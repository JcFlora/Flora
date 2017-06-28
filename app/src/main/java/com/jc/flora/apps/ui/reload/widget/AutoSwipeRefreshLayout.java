package com.jc.flora.apps.ui.reload.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Samurai on 2017/5/25.
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout3 {

    public AutoSwipeRefreshLayout(Context context) {
        super(context);
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void autoRefresh() {
        if(isRefreshing()){
            return;
        }
        try {
            Field mCircleView = SwipeRefreshLayout3.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            //自动刷新的缩放动画
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(progress, "scaleX", 0f, 1f);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(progress, "scaleY", 0f, 1f);
            animatorX.setDuration(500);
            animatorY.setDuration(500);
            animatorX.start();
            animatorY.start();

            Method setRefreshing = SwipeRefreshLayout3.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}