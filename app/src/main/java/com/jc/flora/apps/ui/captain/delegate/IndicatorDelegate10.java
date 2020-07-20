package com.jc.flora.apps.ui.captain.delegate;

import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shijincheng on 2017/5/12.
 */
public class IndicatorDelegate10 implements ViewPager.OnPageChangeListener{

    private int mCount;
    private String[] mTitles;
    private float mTextSize;
    private int mTextColorChecked,mTextColorUnchecked;
    private int[] mIconResNormal;
    private int[] mIconResFocus;
    private LinearLayout mLayoutCaptainIndicators;

    private TextView[] mBtnTabs;
    private OnTabIndicatorCheckedChangeListener mOnTabIndicatorCheckedChangeListener;

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

    public void setOnTabIndicatorCheckedChangeListener(OnTabIndicatorCheckedChangeListener l) {
        mOnTabIndicatorCheckedChangeListener = l;
    }

    public void setOnRudderClickListener(View.OnClickListener onRudderClickListener) {
        mOnRudderClickListener = onRudderClickListener;
    }

    public void init(int startIndex){
        initViews();
        onChecked(startIndex);
    }

    private void initViews(){
        if(mLayoutCaptainIndicators == null){
            return;
        }
        mBtnTabs = new TextView[mCount];
        for (int i = 0; i < mCount; i++) {
            final int j = i;
            mBtnTabs[j] = new TextView(mLayoutCaptainIndicators.getContext());
            mBtnTabs[j].setText(mTitles[j]);
            mBtnTabs[j].setTextSize(mTextSize);
            mBtnTabs[j].setTextColor(mTextColorUnchecked);
            mBtnTabs[j].setGravity(Gravity.CENTER);
            mBtnTabs[j].setCompoundDrawablesWithIntrinsicBounds(0, mIconResNormal[j], 0, 0);
            // 在中间位置处拦截，添加舵控件图片和事件
            if(mIconRudder > 0 && j == mCount/2){
                createRudderView();
            }
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, wrap);
            params.weight = 1;
            mLayoutCaptainIndicators.addView(mBtnTabs[j], params);
            mBtnTabs[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAllChecked();
                    onChecked(j);
                }
            });
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

    private void clearAllChecked() {
        for (int i = 0; i < mCount; i++) {
            mBtnTabs[i].setTextColor(mTextColorUnchecked);
            mBtnTabs[i].setCompoundDrawablesWithIntrinsicBounds(0, mIconResNormal[i], 0, 0);
        }
    }

    private void onChecked(int position) {
        mBtnTabs[position].setTextColor(mTextColorChecked);
        mBtnTabs[position].setCompoundDrawablesWithIntrinsicBounds(0, mIconResFocus[position], 0, 0);
        if(mOnTabIndicatorCheckedChangeListener != null){
            mOnTabIndicatorCheckedChangeListener.onTabIndicatorCheckedChanged(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        clearAllChecked();
        mBtnTabs[position].setTextColor(mTextColorChecked);
        mBtnTabs[position].setCompoundDrawablesWithIntrinsicBounds(0, mIconResFocus[position], 0, 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public interface OnTabIndicatorCheckedChangeListener{
        void onTabIndicatorCheckedChanged(int checkedPosition);
    }

}
