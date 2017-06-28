package com.jc.flora.apps.ui.captain.delegate;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/5/12.
 */
public class IndicatorDelegate4 {

    private static final int COLOR_CHECKED = Color.parseColor("#d12147");
    private static final int COUNT = 5;
    private static final String[] TITLES = {"首页", "分类", "发现", "购物车", "我的考拉"};
    private static final int[] ICON_RES_NORMAL = {R.drawable.captain_home_normal,
            R.drawable.captain_category_normal, R.drawable.captain_discovery_normal,
            R.drawable.captain_cart_normal, R.drawable.captain_kaola_normal};
    private static final int[] ICON_RES_FOCUS = {R.drawable.captain_home_focus,
            R.drawable.captain_category_focus, R.drawable.captain_discovery_focus,
            R.drawable.captain_cart_focus, R.drawable.captain_kaola_focus};

    private LinearLayout mLayoutCaptainIndicators;
    private TextView[] mBtnTabs = new TextView[COUNT];

    public void setLayoutCaptainIndicators(LinearLayout layoutCaptainIndicators) {
        mLayoutCaptainIndicators = layoutCaptainIndicators;
    }

    public void init(){
        initViews();
        onChecked(0);
    }

    private void initViews(){
        if(mLayoutCaptainIndicators == null){
            return;
        }
        for (int i = 0; i < COUNT; i++) {
            final int j = i;
            mBtnTabs[j] = new TextView(mLayoutCaptainIndicators.getContext());
            mBtnTabs[j].setText(TITLES[j]);
            mBtnTabs[j].setTextSize(11);
            mBtnTabs[j].setTextColor(Color.BLACK);
            mBtnTabs[j].setGravity(Gravity.CENTER);
            mBtnTabs[j].setCompoundDrawablesWithIntrinsicBounds(0, ICON_RES_NORMAL[j], 0, 0);
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
        for (int i = 0; i < COUNT; i++) {
            mBtnTabs[i].setTextColor(Color.BLACK);
            mBtnTabs[i].setCompoundDrawablesWithIntrinsicBounds(0, ICON_RES_NORMAL[i], 0, 0);
        }
    }

    private void onChecked(int position) {
        mBtnTabs[position].setTextColor(COLOR_CHECKED);
        mBtnTabs[position].setCompoundDrawablesWithIntrinsicBounds(0, ICON_RES_FOCUS[position], 0, 0);
    }

}
