package com.jc.flora.apps.ui.banner.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by shijincheng on 2017/5/3.
 */
public class IndicatorDelegate10 extends Fragment {

    private RelativeLayout mLayoutBanner;
    private ViewPager mViewPager;
    private LinearLayout mLayoutBannerIndicators;
    private ImageView[] mIvIndicators;

    private Position mIndicatorPosition;
    private int mIndicatorsLayoutXMargin;
    private int mIndicatorsLayoutYMargin;
    private int mIndicatorsLayoutPadding;
    private int mBgResId;
    private int mSelectedResId, mUnselectedResId;
    private int mIndicatorSpace;

    public void setLayoutBanner(RelativeLayout layoutBanner) {
        mLayoutBanner = layoutBanner;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
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

    public void setBgResId(int bgResId) {
        mBgResId = bgResId;
    }

    public void setIndicatorResId(int unselectedResId, int selectedResId){
        mUnselectedResId = unselectedResId;
        mSelectedResId = selectedResId;
    }

    public void setIndicatorSpace(int indicatorSpace) {
        mIndicatorSpace = indicatorSpace;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void initIndicators(int length){
        if(length <= 1 || mLayoutBanner == null || mViewPager == null){
            return;
        }
        initIndicatorsLayout();
        initIndicatorItems(length);
        initIndicatorListener(length);
        setIndicatorChecked(0);
    }

    private void initIndicatorsLayout(){
        mLayoutBannerIndicators = new LinearLayout(mLayoutBanner.getContext());
        mLayoutBannerIndicators.setOrientation(LinearLayout.HORIZONTAL);
        int wrap = RelativeLayout.LayoutParams.WRAP_CONTENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wrap, wrap);
        int x = mIndicatorsLayoutXMargin;
        int y = mIndicatorsLayoutYMargin;
        params.setMargins(x, y, x, y);
        int p = mIndicatorsLayoutPadding;
        mLayoutBannerIndicators.setPadding(p, p, p, p);
        if(mBgResId > 0){
            mLayoutBannerIndicators.setBackgroundResource(mBgResId);
        }
        setAlignByPosition(params);
        mLayoutBanner.addView(mLayoutBannerIndicators, params);
    }

    private void initIndicatorItems(int length) {
        mIvIndicators = new ImageView[length];
        for (int i = 0; i < length; i++) {
            mIvIndicators[i] = new ImageView(mViewPager.getContext());
            if(mUnselectedResId > 0){
                mIvIndicators[i].setImageResource(mUnselectedResId);
            }
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrap, wrap);
            mLayoutBannerIndicators.addView(mIvIndicators[i], params);
            if (i != (length - 1)) {
                Space space = new Space(mViewPager.getContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(mIndicatorSpace, 1);
                mLayoutBannerIndicators.addView(space, p);
            }
        }
    }

    private void initIndicatorListener(final int length) {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setIndicatorChecked(position % length);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setAlignByPosition(RelativeLayout.LayoutParams params)
    {
        switch (mIndicatorPosition)
        {
            case CENTER_BOTTOM:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case CENTER_TOP:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case LEFT_BOTTOM:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case LEFT_TOP:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case RIGHT_BOTTOM:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case RIGHT_TOP:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
        }
    }

    private void setIndicatorChecked(int position) {
        for (ImageView mIvIndicator : mIvIndicators) {
            if(mUnselectedResId > 0){
                mIvIndicator.setImageResource(mUnselectedResId);
            }
        }
        if(mSelectedResId > 0){
            mIvIndicators[position].setImageResource(mSelectedResId);
        }
    }

    public enum Position {
        CENTER_BOTTOM, RIGHT_BOTTOM, LEFT_BOTTOM, CENTER_TOP, RIGHT_TOP, LEFT_TOP
    }

}