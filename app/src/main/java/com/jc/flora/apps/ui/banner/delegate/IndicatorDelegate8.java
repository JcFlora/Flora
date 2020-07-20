package com.jc.flora.apps.ui.banner.delegate;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/5/3.
 */
public class IndicatorDelegate8 extends Fragment {

    private ViewPager mViewPager;
    private LinearLayout mLayoutBannerIndicators;
    private ImageView[] mIvIndicators;

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void setLayoutBannerIndicators(LinearLayout layoutBannerIndicators) {
        mLayoutBannerIndicators = layoutBannerIndicators;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void initIndicators(final int length){
        if(length <= 0 || mViewPager == null || mLayoutBannerIndicators == null){
            return;
        }
        mIvIndicators = new ImageView[length];
        for (int i = 0; i < length; i++) {
            mIvIndicators[i] = new ImageView(mViewPager.getContext());
            mIvIndicators[i].setImageResource(R.drawable.dot_unselected);
            int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrap, wrap);
            mLayoutBannerIndicators.addView(mIvIndicators[i], params);
            if (i != (length - 1)) {
                Space space = new Space(mViewPager.getContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 1);
                mLayoutBannerIndicators.addView(space, p);
            }
        }
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
        setIndicatorChecked(0);
    }

    private void setIndicatorChecked(int position) {
        for (ImageView mIvIndicator : mIvIndicators) {
            mIvIndicator.setImageResource(R.drawable.dot_unselected);
        }
        mIvIndicators[position].setImageResource(R.drawable.dot_selected);
    }

}
