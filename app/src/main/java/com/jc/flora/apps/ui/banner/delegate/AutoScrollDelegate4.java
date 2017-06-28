package com.jc.flora.apps.ui.banner.delegate;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by Samurai on 2017/4/30.
 */
public class AutoScrollDelegate4 extends Fragment {

    private static final int WHAT_AUTO_PLAY = 1000;

    private ViewPager mViewPager;

    /** 是否自动切换 */
    private boolean mIsAutoPlay = false;
    /** 自动切换周期 */
    private int mAutoPlayDuration = 5000;
    /** 自动切换动画持续时间 */
    private int mSliderTransformDuration = 1000;

    public void setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
    }

    public void setIsAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
    }

    public void setAutoPlayDuration(int autoPlayDuration) {
        mAutoPlayDuration = autoPlayDuration;
    }

    public void setAnimDuration(int animDuration) {
        mSliderTransformDuration = animDuration;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            if(mViewPager != null){
                initSliderTransformDuration();
            }
        }
    }

    private void initSliderTransformDuration() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), null);
            scroller.setDuration(mSliderTransformDuration);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(WHAT_AUTO_PLAY);
    }

    public void startAutoPlay() {
        // 避免重复消息
        stopAutoPlay();
        if (mIsAutoPlay) {
            mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPlayDuration);
        }
    }

    public void stopAutoPlay() {
        if (mIsAutoPlay) {
            mHandler.removeMessages(WHAT_AUTO_PLAY);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mViewPager != null && mIsAutoPlay && msg.what == WHAT_AUTO_PLAY) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPlayDuration);
            }
            return false;
        }
    });

    private static class FixedSpeedScroller extends Scroller {
        private int mDuration = 1000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int time) {
            mDuration = time;
        }
    }

}
