package com.jc.flora.apps.ui.banner.projects;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BannerViewPager extends ViewPager {

    /** 是否可以滑动的标志 */
    private boolean mIsPagingEnabled = true;

    public BannerViewPager(Context context) {
        super(context);
    }

    /**
     * @param context 上下文环境
     * @param attrs
     */
    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event) && mIsPagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event) && mIsPagingEnabled;
    }

    /**
     * 设置是否可以滑动
     * @param enabled
     */
    public void setPagingEnabled(boolean enabled) {
        mIsPagingEnabled = enabled;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mIsPagingEnabled) {
            super.scrollTo(x, y);
        }
    }

}
