package com.jc.flora.apps.scene.guider.delegate;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.guider.projects.Guider4Fragment;

/**
 * Created by Samurai on 2017/6/7.
 */
public class GuiderDelegate4 {

    private static final int COUNT = 3;

    private AppCompatActivity mActivity;

    private ViewPager mVpGuider;
    private View mBtnSkip;
    private View mBtnNext;
    private View mBtnFinish;
    private LinearLayout mLayoutGuiderIndicators;

    private ImageView[] mIvIndicators = new ImageView[COUNT];
    private int[] mBgColors = {Color.parseColor("#55aced"),
            Color.parseColor("#fe4e4e"),Color.parseColor("#9567fe")};
    private int mCurrentPosition;

    private static final int[] PAGE_LAYOUT_RES = {R.layout.layout_guider_intro0,
            R.layout.layout_guider_intro1, R.layout.layout_guider_intro2};

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setVpGuider(ViewPager vpGuider) {
        mVpGuider = vpGuider;
    }

    public void setBtnSkip(View btnSkip) {
        mBtnSkip = btnSkip;
    }

    public void setBtnNext(View btnNext) {
        mBtnNext = btnNext;
    }

    public void setBtnFinish(View btnFinish) {
        mBtnFinish = btnFinish;
    }

    public void setLayoutGuiderIndicators(LinearLayout layoutGuiderIndicators) {
        mLayoutGuiderIndicators = layoutGuiderIndicators;
    }

    public void init(){
        initIndicators();
        initViews();
        setAdapter();
        setIndicatorChecked(0);
    }

    private void initIndicators() {
        for (int i = 0; i < COUNT; i++) {
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
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, mBgColors[position], mBgColors[position == 2 ? position : position + 1]);
                mVpGuider.setBackgroundColor(colorUpdate);

            }
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                setIndicatorChecked(position);
                mVpGuider.setBackgroundColor(mBgColors[position]);
                mBtnSkip.setVisibility(position == COUNT-1 ? View.GONE : View.VISIBLE);
                mBtnNext.setVisibility(position == COUNT-1 ? View.GONE : View.VISIBLE);
                mBtnFinish.setVisibility(position == COUNT-1 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(mActivity,"你点击了跳过");
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPosition++;
                mVpGuider.setCurrentItem(mCurrentPosition);
            }
        });
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(mActivity,"你点击了开始");
            }
        });
    }

    private void setIndicatorChecked(int position) {
        for (ImageView mIvIndicator : mIvIndicators) {
            mIvIndicator.setImageResource(R.drawable.guider_star_default);
        }
        mIvIndicators[position].setImageResource(R.drawable.guider_star_light);
    }

    private void setAdapter(){
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(mActivity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Guider4Fragment.newInstance(PAGE_LAYOUT_RES[position]);
            }
            @Override
            public int getCount() {
                return COUNT;
            }
        };
        mVpGuider.setAdapter(adapter);
    }

}
