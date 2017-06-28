package com.jc.flora.apps.scene.guider.delegate;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.guider.projects.GuiderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/5/30.
 */
public class GuiderDelegate3 {

    private AppCompatActivity mActivity;

    private ViewPager mVpGuider;
    private List<View> mViews;
    private View mBtnPass;
    private View mBtnStart;
    private LinearLayout mLayoutGuiderIndicators;
    private static final int[] PAGE_RES = {R.drawable.guider_page1,
            R.drawable.guider_page2, R.drawable.guider_page3,
            R.drawable.guider_page4, R.drawable.guider_page5};
    private static final int[] ICON_RES_NORMAL = {R.drawable.guider_u_indicator1,
            R.drawable.guider_u_indicator2, R.drawable.guider_u_indicator3,
            R.drawable.guider_u_indicator4, R.drawable.guider_u_indicator5};
    private static final int[] ICON_RES_FOCUS = {R.drawable.guider_s_indicator1,
            R.drawable.guider_s_indicator2, R.drawable.guider_s_indicator3,
            R.drawable.guider_s_indicator4, R.drawable.guider_s_indicator5};

    private ImageView[] mIvIndicators = new ImageView[5];

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setVpGuider(ViewPager vpGuider) {
        mVpGuider = vpGuider;
    }

    public void setBtnPass(View btnPass) {
        mBtnPass = btnPass;
    }

    public void setBtnStart(View btnStart) {
        mBtnStart = btnStart;
    }

    public void setLayoutGuiderIndicators(LinearLayout layoutGuiderIndicators) {
        mLayoutGuiderIndicators = layoutGuiderIndicators;
    }

    public void init(){
        initIndicators();
        initViews();
        initData();
        setAdapter();
        setIndicatorChecked(0);
    }

    private void initIndicators() {
        for (int i = 0; i < 5; i++) {
            mIvIndicators[i] = new ImageView(mActivity);
            mIvIndicators[i].setImageResource(ICON_RES_NORMAL[i]);
            mIvIndicators[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(54, 54);
            mLayoutGuiderIndicators.addView(mIvIndicators[i], params);
            if (i != (4)) {
                Space space = new Space(mActivity);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(10, 1);
                mLayoutGuiderIndicators.addView(space, p);
            }
        }
    }

    private void initViews() {
        mVpGuider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setIndicatorChecked(position);
                mBtnPass.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                mBtnStart.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(mActivity,"你点击了跳过");
            }
        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(mActivity,"你点击了开始");
            }
        });
    }

    private void initData() {
        mViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageView iv = new ImageView(mActivity);
            iv.setImageResource(PAGE_RES[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mViews.add(iv);
        }
    }

    private void setAdapter() {
        GuiderAdapter adapter = new GuiderAdapter(mViews);
        mVpGuider.setAdapter(adapter);
    }

    private void setIndicatorChecked(int position) {
        for (int i = 0; i < 5; i++) {
            mIvIndicators[i].setImageResource(ICON_RES_NORMAL[i]);
        }
        mIvIndicators[position].setImageResource(ICON_RES_FOCUS[position]);
    }

}
