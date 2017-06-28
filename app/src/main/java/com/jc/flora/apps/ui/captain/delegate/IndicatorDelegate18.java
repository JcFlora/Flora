package com.jc.flora.apps.ui.captain.delegate;

import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.flora.apps.ui.badge.widget.BadgeView;
import com.jc.flora.apps.ui.captain.widget.GradientIconView;
import com.jc.flora.apps.ui.captain.widget.GradientTextView;

/**
 * Created by shijincheng on 2017/5/25.
 */
public class IndicatorDelegate18 implements ViewPager.OnPageChangeListener{

    private int mCount;
    private int mPreviousIndex  = -1;
    private int mCurrentIndex = -1;
    private String[] mTitles;
    private float mTextSize;
    private int mTextColorChecked,mTextColorUnchecked;
    private int[] mIconResNormal;
    private int[] mIconResFocus;
    private LinearLayout mLayoutCaptainIndicators;

    private CaptainTab[] mTabs;
    private OnTabIndicatorClickListener mOnTabIndicatorClickListener;

    // 以下为添加的Rudder部分
    // 舵控件图片
    private int mIconRudder;
    // 舵控件图片外间距
    private int mRudderMargin;
    // 舵控件事件
    private View.OnClickListener mOnRudderClickListener;
    // 舵控件是否放大
    private boolean mIsRudderBig;
    // 舵控件宽度
    private int mRudderWidth;
    // 舵控件高度
    private int mRudderHeight;

    public void setCount(int count) {
        mCount = count;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
    }

    public void setTextColor(int textColorUnchecked, int textColorChecked) {
        mTextColorUnchecked = textColorUnchecked;
        mTextColorChecked = textColorChecked;
    }

    public void setIconResNormal(int[] iconResNormal) {
        mIconResNormal = iconResNormal;
    }

    public void setIconResFocus(int[] iconResFocus) {
        mIconResFocus = iconResFocus;
    }

    /**
     * 设置舵控件的图片，只要调用此方法，就说明使用了舵式导航模式
     * @param iconRudder
     */
    public void setIconRudder(int iconRudder) {
        mIconRudder = iconRudder;
    }

    public void setRudderMargin(int rudderMargin) {
        mRudderMargin = rudderMargin;
    }

    public void setRudderBig(boolean rudderBig) {
        mIsRudderBig = rudderBig;
    }

    public void setRudderWidth(int rudderWidth) {
        mRudderWidth = rudderWidth;
    }

    public void setRudderHeight(int rudderHeight) {
        mRudderHeight = rudderHeight;
    }

    public void setLayoutCaptainIndicators(LinearLayout layoutCaptainIndicators) {
        mLayoutCaptainIndicators = layoutCaptainIndicators;
    }

    public void setOnTabIndicatorClickListener(OnTabIndicatorClickListener l) {
        mOnTabIndicatorClickListener = l;
    }

    public void setOnRudderClickListener(View.OnClickListener onRudderClickListener) {
        mOnRudderClickListener = onRudderClickListener;
    }

    public void init(int startIndex, boolean isWxMode){
        initViews(isWxMode);
        onChecked(startIndex);
    }

    public void setBadgeText(int index, String s){
        mTabs[index].setBadgeText(index, s);
    }

    public void rollback(){
        onChecked(mPreviousIndex);
    }

    public boolean back2Tab0(){
        if(mCurrentIndex == 0){
            return false;
        }else{
            onChecked(0);
            return true;
        }
    }

    private void initViews(boolean isWxMode){
        if(mLayoutCaptainIndicators == null){
            return;
        }
        mTabs = new CaptainTab[mCount];
        for (int i = 0; i < mCount; i++) {
            // 在中间位置处拦截，添加舵控件图片和事件
            if(mIconRudder > 0 && i == mCount/2){
                createRudderView();
            }
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, wrap);
            params.weight = 1;
            mTabs[i] = isWxMode ? new WxTab() : new NormalTab();
            mLayoutCaptainIndicators.addView(mTabs[i].createTab(i), params);
        }
    }

    /**
     * 添加舵控件图片和事件
     */
    private void createRudderView(){
        if(mIconRudder <= 0){
            return;
        }
        ImageView ivRudder = new ImageView(mLayoutCaptainIndicators.getContext());
        ivRudder.setImageResource(mIconRudder);
        int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrap, wrap);
        params.leftMargin = mRudderMargin;
        params.rightMargin = mRudderMargin;
        if(mIsRudderBig){
            params.width = mRudderWidth;
            params.height = mRudderHeight;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        }
        mLayoutCaptainIndicators.addView(ivRudder, params);
        if(mOnRudderClickListener != null){
            ivRudder.setOnClickListener(mOnRudderClickListener);
        }
    }

    private void onChecked(int position) {
        if (position < 0 || position >= mCount) {
            return;
        }
        if(updateIndex(position)){
            if(mPreviousIndex >= 0 && mPreviousIndex <mCount){
                mTabs[mPreviousIndex].onUnchecked(mPreviousIndex);
            }
            if(mCurrentIndex>= 0 && mCurrentIndex <mCount){
                mTabs[mCurrentIndex].onChecked(mCurrentIndex);
                if(mOnTabIndicatorClickListener != null){
                    mOnTabIndicatorClickListener.onTabIndicatorCheckedChanged(mCurrentIndex);
                }
            }
        }else if(mOnTabIndicatorClickListener != null){
            mOnTabIndicatorClickListener.onTabIndicatorReClick(position);
        }
    }

    private boolean updateIndex(int position){
        // 当前tab再次点击时，不进行任何操作
        if (position == mCurrentIndex){
            return false;
        }
        // 更新前一个索引
        mPreviousIndex = mCurrentIndex;
        // 更新当前索引
        mCurrentIndex = position;
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            mTabs[position].onPageScrolled(position, 1 - positionOffset);
            mTabs[position + 1].onPageScrolled(position + 1, positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position < 0 || position >= mCount) {
            return;
        }
        if(updateIndex(position)){
            if (mPreviousIndex >= 0 && mPreviousIndex < mCount) {
                mTabs[mPreviousIndex].onUnchecked(mPreviousIndex);
            }
            if (mCurrentIndex >= 0 && mCurrentIndex < mCount) {
                mTabs[mCurrentIndex].onChecked(mCurrentIndex);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public interface OnTabIndicatorClickListener {
        void onTabIndicatorCheckedChanged(int checkedPosition);
        void onTabIndicatorReClick(int position);
    }

    private class NormalTab implements CaptainTab{
        TextView mBtn;
        @Override
        public View createTab(final int position) {
            mBtn = new TextView(mLayoutCaptainIndicators.getContext());
            mBtn.setText(mTitles[position]);
            mBtn.setTextSize(mTextSize);
            mBtn.setTextColor(mTextColorUnchecked);
            mBtn.setGravity(Gravity.CENTER);
            mBtn.setCompoundDrawablesWithIntrinsicBounds(0, mIconResNormal[position], 0, 0);
            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IndicatorDelegate18.this.onChecked(position);
                }
            });
            return mBtn;
        }

        @Override
        public void onUnchecked(int position) {
            mBtn.setTextColor(mTextColorUnchecked);
            mBtn.setCompoundDrawablesWithIntrinsicBounds(0, mIconResNormal[position], 0, 0);
        }

        @Override
        public void onChecked(int position) {
            mBtn.setTextColor(mTextColorChecked);
            mBtn.setCompoundDrawablesWithIntrinsicBounds(0, mIconResFocus[position], 0, 0);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset) {
        }

        @Override
        public void setBadgeText(int index, String s){
            BadgeView bv = new BadgeView(mBtn.getContext(), mBtn);
            bv.setBadgeText(s);
        }

    }

    private class WxTab implements CaptainTab{

        private LinearLayout mLayout;
        private GradientIconView mIconView;
        private GradientTextView mTextView;

        @Override
        public View createTab(final int position) {
            mLayout = new LinearLayout(mLayoutCaptainIndicators.getContext());
            mLayout.setGravity(Gravity.CENTER);
            mLayout.setOrientation(LinearLayout.VERTICAL);
            // 添加图标
            mIconView = new GradientIconView(mLayoutCaptainIndicators.getContext());
            mIconView.setTopIconView(mIconResFocus[position]);
            mIconView.setBottomIconView(mIconResNormal[position]);
            mIconView.setIconAlpha(0f);
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(wrap, wrap);
            mLayout.addView(mIconView,iconParams);
            // 添加文字
            mTextView = new GradientTextView(mLayoutCaptainIndicators.getContext());
            mTextView.setGradientText(mTitles[position]);
            mTextView.setGradientTextSize(mTextSize);
            mTextView.setTopTextViewColor(mTextColorChecked);
            mTextView.setBottomTextViewColor(mTextColorUnchecked);
            mTextView.setTextViewAlpha(0f);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(wrap, wrap);
            mLayout.addView(mTextView, textParams);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IndicatorDelegate18.this.onChecked(position);
                }
            });
            return mLayout;
        }

        @Override
        public void onUnchecked(int position) {
            mIconView.setIconAlpha(0f);
            mTextView.setTextViewAlpha(0f);
        }

        @Override
        public void onChecked(int position) {
            mIconView.setIconAlpha(1f);
            mTextView.setTextViewAlpha(1f);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset) {
            mIconView.setIconAlpha(positionOffset);
            mTextView.setTextViewAlpha(positionOffset);
        }

        @Override
        public void setBadgeText(int index, String s){
            mLayoutCaptainIndicators.setClipChildren(false);
            BadgeView bv = new BadgeView(mLayout.getContext(), mIconView);
            bv.setBadgeText(s);
        }
    }

    private interface CaptainTab{
        View createTab(int position);
        void onUnchecked(int position);
        void onChecked(int position);
        void onPageScrolled(int position, float positionOffset);
        void setBadgeText(int index, String s);
    }

}
