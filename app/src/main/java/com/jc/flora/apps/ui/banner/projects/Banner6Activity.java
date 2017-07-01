package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner6Activity extends AppCompatActivity {

    private ViewPager mVpBanner;
    private LinearLayout mLayoutBannerIndicators;
    private List<View> mViews;
    private AutoScrollDelegate4 mDelegate;

    private ImageView[] mIvIndicators = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("动态添加指示器item");
        setContentView(R.layout.activity_banner6);
        findViews();
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    private void findViews() {
        mVpBanner = (ViewPager) findViewById(R.id.vp_banner);
        mLayoutBannerIndicators = (LinearLayout) findViewById(R.id.layout_banner_indicators);
    }

    private void initData() {
        mViews = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        ImageView iv2 = new ImageView(this);
        ImageView iv3 = new ImageView(this);
        ImageView iv4 = new ImageView(this);
        iv1.setImageResource(R.drawable.banner1);
        iv2.setImageResource(R.drawable.banner2);
        iv3.setImageResource(R.drawable.banner3);
        iv4.setImageResource(R.drawable.banner4);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);
        iv4.setScaleType(ImageView.ScaleType.FIT_XY);
        mViews.add(iv1);
        mViews.add(iv2);
        mViews.add(iv3);
        mViews.add(iv4);
    }

    private void setAdapter(){
        BannerLoopPagerAdapter adapter = new BannerLoopPagerAdapter(mViews);
        mVpBanner.setAdapter(adapter);
        mVpBanner.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mViews.size());
    }

    private void initAutoScroll() {
        mDelegate = new AutoScrollDelegate4();
        mDelegate.setViewPager(mVpBanner);
        mDelegate.setIsAutoPlay(true);
        mDelegate.setAutoPlayDuration(3000);
        mDelegate.setAnimDuration(1200);
        mDelegate.addToActivity(this, "autoScroll");
    }

    private void initIndicators(){
        for (int i = 0; i < 4; i++) {
            mIvIndicators[i] = new ImageView(this);
            mIvIndicators[i].setImageResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            mLayoutBannerIndicators.addView(mIvIndicators[i], params);
            if (i != 3) {
                Space space = new Space(this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 1);
                mLayoutBannerIndicators.addView(space, p);
            }
        }
        mVpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setIndicatorChecked(position%4);
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

    //todo 粗暴方案，有bug
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mDelegate == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDelegate.stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDelegate.startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
