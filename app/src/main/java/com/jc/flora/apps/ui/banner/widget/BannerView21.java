package com.jc.flora.apps.ui.banner.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate20;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate21;
import com.jc.flora.apps.ui.banner.projects.BannerLoopPagerAdapter;
import com.jc.flora.apps.ui.banner.transformer.BasePageTransformer;
import com.jc.flora.apps.ui.banner.transformer.TransitionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/8/20.
 */
public class BannerView21 extends RelativeLayout {

    private BannerViewPager mVpBanner;
    /** 是否自动切换 */
    private boolean mIsAutoPlay = false;
    /** 自动切换周期 */
    private int mAutoPlayDuration = 5000;
    /** 自动切换动画持续时间 */
    private int mSliderTransformDuration = 1000;

    private Position mIndicatorPosition;
    private int mIndicatorsLayoutXMargin;
    private int mIndicatorsLayoutYMargin;
    private int mIndicatorsLayoutPadding;
    private int mBgResId;
    private int mSelectedResId, mUnselectedResId;
    private int mIndicatorSpace;

    private boolean mIsFloated;

    private int mImageLength;
    private List<View> mViews;
    private AutoScrollDelegate20 mAutoScrollDelegate;
    private IndicatorDelegate21 mIndicatorDelegate;
    private BannerDataDelivery mBannerDataDelivery;
    private OnBannerItemClickListener mOnBannerItemClickListener;
    private TransitionEffect mTransitionEffect;

    public BannerView21(Context context) {
        super(context);
    }

    public BannerView21(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView21(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView21(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    public void setIndicatorPosition(Position indicatorPosition) {
        mIndicatorPosition = indicatorPosition;
    }

    public void setIndicatorsLayoutXMargin(int indicatorsLayoutXMargin){
        mIndicatorsLayoutXMargin = indicatorsLayoutXMargin;
    }

    public void setIndicatorsLayoutYMargin(int indicatorsLayoutYMargin){
        mIndicatorsLayoutYMargin = indicatorsLayoutYMargin;
    }

    public void setIndicatorsLayoutPadding(int indicatorsLayoutPadding) {
        mIndicatorsLayoutPadding = indicatorsLayoutPadding;
    }

    public void setIndicatorsLayoutBgResId(int bgResId) {
        mBgResId = bgResId;
    }

    public void setIndicatorResId(int unselectedResId, int selectedResId){
        mUnselectedResId = unselectedResId;
        mSelectedResId = selectedResId;
    }

    public void setIndicatorSpace(int indicatorSpace) {
        mIndicatorSpace = indicatorSpace;
    }

    public void setIndicatorFloated(boolean floated) {
        mIsFloated = floated;
    }

    public void setBannerDataDelivery(BannerDataDelivery dataDelivery) {
        mBannerDataDelivery = dataDelivery;
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener l) {
        mOnBannerItemClickListener = l;
    }

    public void setPageTransformer(TransitionEffect effect){
        mTransitionEffect = effect;
    }

    public void start() {
        removeAllViews();
        addViewPager();
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    public void reload(){
        // 刷新前销毁之前的自动翻页
        if(mAutoScrollDelegate != null){
            mAutoScrollDelegate.destroy();
        }
        start();
        mAutoScrollDelegate.startAutoPlay();
    }

    private void addViewPager(){
        mVpBanner = new BannerViewPager(getContext());
        addView(mVpBanner);
        if(mTransitionEffect != null){
            BasePageTransformer.setPageTransformer(mVpBanner, mTransitionEffect);
        }
    }

    private void initData() {
        if(mBannerDataDelivery != null){
            mImageLength = mBannerDataDelivery.getCount();
        }
        if (mImageLength == 1) {
            mVpBanner.setPagingEnabled(false);
        }
        int falseLength = (mImageLength == 1 || mImageLength == 2) ? (mImageLength + 2) : mImageLength;
        mViews = new ArrayList<>(falseLength);
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            final int j = i % mImageLength;
            if(mBannerDataDelivery != null){
                mBannerDataDelivery.deliverImage(iv, j);
            }
            if(mOnBannerItemClickListener != null){
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBannerItemClickListener.onItemClick(j);
                    }
                });
            }
        }
    }

    private void setAdapter() {
        BannerLoopPagerAdapter adapter = new BannerLoopPagerAdapter(mViews);
        mVpBanner.setAdapter(adapter);
        if (mImageLength == 1) {
            mVpBanner.setCurrentItem(0);
        } else {
            mVpBanner.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImageLength);
        }
    }

    private void initAutoScroll() {
        if (mImageLength <= 1) {
            return;
        }
        mAutoScrollDelegate = new AutoScrollDelegate20();
        mAutoScrollDelegate.setViewPager(mVpBanner);
        mAutoScrollDelegate.setIsAutoPlay(mIsAutoPlay);
        mAutoScrollDelegate.setAutoPlayDuration(mAutoPlayDuration);
        mAutoScrollDelegate.setAnimDuration(mSliderTransformDuration);
        mAutoScrollDelegate.initAutoScroll();
    }

    private void initIndicators() {
        if (mImageLength <= 1) {
            return;
        }
        mIndicatorDelegate = new IndicatorDelegate21();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBanner(this);
        mIndicatorDelegate.setIndicatorPosition(mIndicatorPosition);
        mIndicatorDelegate.setIndicatorsLayoutXMargin(mIndicatorsLayoutXMargin);
        mIndicatorDelegate.setIndicatorsLayoutYMargin(mIndicatorsLayoutYMargin);
        mIndicatorDelegate.setIndicatorsLayoutPadding(mIndicatorsLayoutPadding);
        mIndicatorDelegate.setFloated(mIsFloated);
        mIndicatorDelegate.setIndicatorResId(mUnselectedResId, mSelectedResId);
        mIndicatorDelegate.setIndicatorSpace(mIndicatorSpace);
        mIndicatorDelegate.initIndicators(mImageLength);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (mAutoScrollDelegate == null) {
            return;
        }
        if (visibility == VISIBLE) {
            mAutoScrollDelegate.startAutoPlay();
        } else {
            mAutoScrollDelegate.stopAutoPlay();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mAutoScrollDelegate == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mAutoScrollDelegate.stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mAutoScrollDelegate.startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAutoScrollDelegate != null) {
            mAutoScrollDelegate.destroy();
        }
    }

    public enum Position {
        CENTER_BOTTOM, RIGHT_BOTTOM, LEFT_BOTTOM, CENTER_TOP, RIGHT_TOP, LEFT_TOP
    }

    public interface BannerDataDelivery{
        int getCount();
        void deliverImage(ImageView imageView, int position);
    }

    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }

}