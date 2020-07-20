package com.jc.flora.apps.scene.guider.delegate;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.guider.projects.GuiderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/5/30.
 */
public class GuiderDelegate2 {

    private AppCompatActivity mActivity;

    private ViewPager mVpGuider;
    private List<View> mViews;
    private View mBtnPass;
    private View mBtnStart;
    private LinearLayout mLayoutGuiderIndicators;
    private static final int[] PAGE_RES = {R.drawable.guider_page1,
            R.drawable.guider_page2, R.drawable.guider_page3,
            R.drawable.guider_page4, R.drawable.guider_page5};

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
            mIvIndicators[i].setImageResource(R.drawable.guider_star_default);
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrap, wrap);
            mLayoutGuiderIndicators.addView(mIvIndicators[i], params);
            if (i != (4)) {
                Space space = new Space(mActivity);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 1);
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
        for (ImageView mIvIndicator : mIvIndicators) {
            mIvIndicator.setImageResource(R.drawable.guider_star_default);
        }
        mIvIndicators[position].setImageResource(R.drawable.guider_star_light);
    }

}
