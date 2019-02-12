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
public class Banner12Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg"};

    private RelativeLayout mLayoutBanner;
    private ViewPager mVpBanner;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private IndicatorDelegate10 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支持3张以下图片");
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
        int length = IMAGE_URIS.length;
        // 重点说明：ViewPager有预加载缓存，所以数量为2个以下时，必须手动添加多个
        // 同时，ViewPager向右和向左滑动时，destroyItem和instantiateItem的执行顺序不一样，
        // 数量为3个时，向左滑动会先add后remove，导致报错，所以3个需添加到6个保证左滑不报错
        int[] lt3lengths = {0, 4, 4, 6};
        int falseLength = (length <= 3) ? (lt3lengths[length]) : length;
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);
            Glide.with(this).load(IMAGE_URIS[i % length]).into(iv);
            final int j = i % length;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(Banner12Activity.this, "banner" + j);
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
