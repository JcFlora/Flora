package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate10;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner11Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private RelativeLayout mLayoutBanner;
    private ViewPager mVpBanner;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private IndicatorDelegate10 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加点击事件");
        setContentView(R.layout.activity_banner10);
        findViews();
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    private void findViews() {
        mLayoutBanner = (RelativeLayout) findViewById(R.id.layout_banner);
        mVpBanner = (ViewPager) findViewById(R.id.vp_banner);
    }

    private void initData() {
        mViews = new ArrayList<>();
        for (int i = 0, length = IMAGE_URIS.length; i < length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            Glide.with(this).load(IMAGE_URIS[i]).into(iv);
            final int j = i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(Banner11Activity.this, "banner" + j);
                }
            });
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
        mIndicatorDelegate = new IndicatorDelegate10();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBanner(mLayoutBanner);
        mIndicatorDelegate.setIndicatorPosition(IndicatorDelegate10.Position.LEFT_BOTTOM);
        mIndicatorDelegate.setIndicatorsLayoutXMargin(25);
        mIndicatorDelegate.setIndicatorsLayoutYMargin(25);
        mIndicatorDelegate.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mIndicatorDelegate.setIndicatorSpace(20);
        mIndicatorDelegate.initIndicators(IMAGE_URIS.length);
        mIndicatorDelegate.addToActivity(this, "indicator");
    }

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
