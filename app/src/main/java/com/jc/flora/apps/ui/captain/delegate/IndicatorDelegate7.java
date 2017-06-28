package com.jc.flora.apps.ui.captain.delegate;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shijincheng on 2017/5/12.
 */
public class IndicatorDelegate7 {

    private int mCount;
    private String[] mTitles;
    private float mTextSize;
    private int mTextColorChecked,mTextColorUnchecked;
    private int[] mIconResNormal;
    private int[] mIconResFocus;
    private LinearLayout mLayoutCaptainIndicators;

    private TextView[] mBtnTabs;
    private OnTabIndicatorCheckedChangeListener mOnTabIndicatorCheckedChangeListener;

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

    public void setLayoutCaptainIndicators(LinearLayout layoutCaptainIndicators) {
        mLayoutCaptainIndicators = layoutCaptainIndicators;
    }

    public void setOnTabIndicatorCheckedChangeListener(OnTabIndicatorCheckedChangeListener l) {
        mOnTabIndicatorCheckedChangeListener = l;
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

    public interface OnTabIndicatorCheckedChangeListener{
        void onTabIndicatorCheckedChanged(int checkedPosition);
    }

}
