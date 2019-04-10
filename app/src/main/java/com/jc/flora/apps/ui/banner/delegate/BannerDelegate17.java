package com.jc.flora.apps.ui.banner.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.apps.ui.banner.projects.BannerLoopPagerAdapter;
import com.jc.flora.apps.ui.banner.widget.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijincheng on 2017/5/5.
 */
public class BannerDelegate17 extends Fragment {

    private String[] mImageUris;
    private RelativeLayout mLayoutBanner;
    private BannerViewPager mVpBanner;
    /** 是否自动切换 */
    private boolean mIsAutoPlay = false;
    /** 自动切换周期 */
    private int mAutoPlayDuration = 5000;
    /** 自动切换动画持续时间 */
    private int mSliderTransformDuration = 1000;

    private IndicatorDelegate16.Position mIndicatorPosition;
    private int mIndicatorsLayoutXMargin;
    private int mIndicatorsLayoutYMargin;
    private int mIndicatorsLayoutPadding;
    private int mBgResId;
    private int mSelectedResId, mUnselectedResId;
    private int mIndicatorSpace;

    private boolean mIsFloated;

    private String mIdTag;
    private int mImageLength;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private IndicatorDelegate16 mIndicatorDelegate;
    private OnBannerItemClickListener mOnBannerItemClickListener;

    public void setImageUris(String... imageUris) {
        mImageUris = imageUris;
    }

    public void setLayoutBanner(RelativeLayout layoutBanner) {
        mLayoutBanner = layoutBanner;
    }

    public void setViewPager(BannerViewPager vpBanner) {
        mVpBanner = vpBanner;
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

    public void setIndicatorPosition(IndicatorDelegate16.Position indicatorPosition) {
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

    public void setOnBannerItemClickListener(OnBannerItemClickListener l) {
        mOnBannerItemClickListener = l;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mIdTag = tag;
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    private void initData() {
        mImageLength = mImageUris.length;
        if (mImageLength == 1) {
            mVpBanner.setPagingEnabled(false);
        }
        // 重点说明：ViewPager有预加载缓存，所以数量为2个以下时，必须手动添加多个
        // 同时，ViewPager向右和向左滑动时，destroyItem和instantiateItem的执行顺序不一样，
        // 数量为3个时，向左滑动会先add后remove，导致报错，所以3个需添加到6个保证左滑不报错
        int[] lt3lengths = {0, 4, 4, 6};
        int falseLength = (mImageLength <= 3) ? (lt3lengths[mImageLength]) : mImageLength;
        mViews = new ArrayList<>(falseLength);
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            Glide.with(this).load(mImageUris[i % mImageLength]).into(iv);
            final int j = i % mImageLength;
            if(mOnBannerItemClickListener != null){
                iv.setOnClickListener(new View.OnClickListener() {
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
        mAutoScrollDelegate = new AutoScrollDelegate4();
        mAutoScrollDelegate.setViewPager(mVpBanner);
        mAutoScrollDelegate.setIsAutoPlay(mIsAutoPlay);
        mAutoScrollDelegate.setAutoPlayDuration(mAutoPlayDuration);
        mAutoScrollDelegate.setAnimDuration(mSliderTransformDuration);
        mAutoScrollDelegate.addToActivity((AppCompatActivity) getActivity(), mIdTag + ":autoScroll");
    }

    private void initIndicators() {
        if (mImageLength <= 1) {
            return;
        }
        mIndicatorDelegate = new IndicatorDelegate16();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBanner(mLayoutBanner);
        mIndicatorDelegate.setIndicatorPosition(mIndicatorPosition);
        mIndicatorDelegate.setIndicatorsLayoutXMargin(mIndicatorsLayoutXMargin);
        mIndicatorDelegate.setIndicatorsLayoutYMargin(mIndicatorsLayoutYMargin);
        mIndicatorDelegate.setIndicatorsLayoutPadding(mIndicatorsLayoutPadding);
        mIndicatorDelegate.setFloated(mIsFloated);
        mIndicatorDelegate.setIndicatorResId(mUnselectedResId, mSelectedResId);
        mIndicatorDelegate.setIndicatorSpace(mIndicatorSpace);
        mIndicatorDelegate.initIndicators(mImageLength);
        mIndicatorDelegate.addToActivity((AppCompatActivity) getActivity(), mIdTag + ":indicator");
    }

    public void startAutoPlay() {
        if(mAutoScrollDelegate != null){
            mAutoScrollDelegate.startAutoPlay();
        }
    }

    public void stopAutoPlay() {
        if(mAutoScrollDelegate != null){
            mAutoScrollDelegate.stopAutoPlay();
        }
    }

    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }

}
