package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;
import com.jc.flora.apps.ui.banner.delegate.CursorIndicatorDelegate14;
import com.jc.flora.apps.ui.banner.widget.BannerViewPager;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner14Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
                    "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private int mImageLength;

    private RelativeLayout mLayoutBanner;
    private BannerViewPager mVpBanner;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private CursorIndicatorDelegate14 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支持浮动式指示器");
        setContentView(R.layout.activity_banner13);
        findViews();
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    private void findViews() {
        mLayoutBanner = (RelativeLayout) findViewById(R.id.layout_banner);
        mVpBanner = (BannerViewPager) findViewById(R.id.vp_banner);
    }

    private void initData() {
        mImageLength = IMAGE_URIS.length;
        if(mImageLength == 1){
            mVpBanner.setPagingEnabled(false);
        }
        int falseLength = (mImageLength == 1 || mImageLength == 2) ? (mImageLength + 2) : mImageLength;
        mViews = new ArrayList<>(falseLength);
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            Glide.with(this).load(IMAGE_URIS[i % mImageLength]).into(iv);
            final int j = i % mImageLength;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(Banner14Activity.this, "banner" + j);
                }
            });
        }
    }

    private void setAdapter(){
        BannerLoopPagerAdapter adapter = new BannerLoopPagerAdapter(mViews);
        mVpBanner.setAdapter(adapter);
        if(mImageLength == 1){
            mVpBanner.setCurrentItem(0);
        }else{
            mVpBanner.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImageLength);
        }
    }

    private void initAutoScroll() {
        if(mImageLength <= 1){
            return;
        }
        mAutoScrollDelegate = new AutoScrollDelegate4();
        mAutoScrollDelegate.setViewPager(mVpBanner);
        mAutoScrollDelegate.setIsAutoPlay(true);
        mAutoScrollDelegate.setAutoPlayDuration(3000);
        mAutoScrollDelegate.setAnimDuration(1200);
        mAutoScrollDelegate.addToActivity(this, "autoScroll");
    }

    private void initIndicators(){
        if(mImageLength <= 1){
            return;
        }
        mIndicatorDelegate = new CursorIndicatorDelegate14();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBanner(mLayoutBanner);
        mIndicatorDelegate.setIndicatorPosition(CursorIndicatorDelegate14.Position.CENTER_BOTTOM);
        mIndicatorDelegate.setIndicatorsLayoutXMargin(25);
        mIndicatorDelegate.setIndicatorsLayoutYMargin(25);
        mIndicatorDelegate.setIndicatorsLayoutPadding(10);
        mIndicatorDelegate.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mIndicatorDelegate.setIndicatorSpace(20);
        mIndicatorDelegate.initIndicators(mImageLength);
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