package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner8Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private ViewPager mVpBanner;
    private LinearLayout mLayoutBannerIndicators;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private IndicatorDelegate8 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("动态添加指示器item封装");
        setContentView(R.layout.activity_banner7);
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
        for (String imageUri : IMAGE_URIS) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            Glide.with(this).load(imageUri).into(iv);
        }
    }

    private void setAdapter(){
        BannerLoopPagerAdapter adapter = new BannerLoopPagerAdapter(mViews);
        mVpBanner.setAdapter(adapter);
        mVpBanner.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % IMAGE_URIS.length);
    }

    private void initAutoScroll() {
        mAutoScrollDelegate = new AutoScrollDelegate4();
        mAutoScrollDelegate.setViewPager(mVpBanner);
        mAutoScrollDelegate.setIsAutoPlay(true);
        mAutoScrollDelegate.setAutoPlayDuration(3000);
        mAutoScrollDelegate.setAnimDuration(1200);
        mAutoScrollDelegate.addToActivity(this, "autoScroll");
    }

    private void initIndicators(){
        mIndicatorDelegate = new IndicatorDelegate8();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBannerIndicators(mLayoutBannerIndicators);
        mIndicatorDelegate.initIndicators(IMAGE_URIS.length);
        mIndicatorDelegate.addToActivity(this, "indicator");
    }

    //todo 粗暴方案，有bug
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mAutoScrollDelegate == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mAutoScrollDelegate.stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mAutoScrollDelegate.startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
