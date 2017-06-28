package com.jc.flora.apps.ui.banner.delegate;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Samurai on 2017/4/30.
 */
public class AutoScrollDelegate3 extends Fragment {

    private static final int WHAT_AUTO_PLAY = 1000;

    private ViewPager mViewPager;

    /** 是否自动切换 */
    private boolean mIsAutoPlay = false;
    /** 自动切换周期 */
    private int mAutoPlayDuration = 5000;

    public void setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
    }

    public void setIsAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
    }

    public void setAutoPlayDuration(int autoPlayDuration) {
        mAutoPlayDuration = autoPlayDuration;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this,tag).commitAllowingStateLoss();
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

}
