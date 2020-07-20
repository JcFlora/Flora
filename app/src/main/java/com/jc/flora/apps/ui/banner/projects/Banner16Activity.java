package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate16;
import com.jc.flora.apps.ui.banner.widget.BannerViewPager;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner16Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    private int mImageLength;

    private RelativeLayout mLayoutBanner1, mLayoutBanner2;
    private BannerViewPager mVpBanner1, mVpBanner2;
    private List<View> mViews1, mViews2;
    private AutoScrollDelegate4 mAutoScrollDelegate1, mAutoScrollDelegate2;
    private IndicatorDelegate16 mIndicatorDelegate1, mIndicatorDelegate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("合并两种指示器");
        setContentView(R.layout.activity_banner16);
        findViews();
        initData();
        setAdapter();
        initAutoScroll();
        initIndicators();
    }

    private void findViews() {
        mLayoutBanner1 = (RelativeLayout) findViewById(R.id.layout_banner1);
        mVpBanner1 = (BannerViewPager) findViewById(R.id.vp_banner1);
        mLayoutBanner2 = (RelativeLayout) findViewById(R.id.layout_banner2);
        mVpBanner2 = (BannerViewPager) findViewById(R.id.vp_banner2);
    }

    private void initData() {
        mImageLength = IMAGE_URIS.length;
        if(mImageLength == 1){
            mVpBanner1.setPagingEnabled(false);
            mVpBanner2.setPagingEnabled(false);
        }
        // 重点说明：ViewPager有预加载缓存，所以数量为2个以下时，必须手动添加多个
        // 同时，ViewPager向右和向左滑动时，destroyItem和instantiateItem的执行顺序不一样，
        // 数量为3个时，向左滑动会先add后remove，导致报错，所以3个需添加到6个保证左滑不报错
        int[] lt3lengths = {0, 4, 4, 6};
        int falseLength = (mImageLength <= 3) ? (lt3lengths[mImageLength]) : mImageLength;
        mViews1 = new ArrayList<>(falseLength);
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews1.add(iv);
            Glide.with(this).load(IMAGE_URIS[i % mImageLength]).into(iv);
            final int j = i % mImageLength;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(Banner16Activity.this, "banner1_" + j);
                }
            });
        }

        mViews2 = new ArrayList<>(falseLength);
        for (int i = 0; i < falseLength; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews2.add(iv);
            Glide.with(this).load(IMAGE_URIS[i % mImageLength]).into(iv);
            final int j = i % mImageLength;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(Banner16Activity.this, "banner2_" + j);
                }
            });
        }
    }

    private void setAdapter(){
        BannerLoopPagerAdapter adapter = new BannerLoopPagerAdapter(mViews1);
        mVpBanner1.setAdapter(adapter);
        if(mImageLength == 1){
            mVpBanner1.setCurrentItem(0);
        }else{
            mVpBanner1.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImageLength);
        }

        BannerLoopPagerAdapter adapter2 = new BannerLoopPagerAdapter(mViews2);
        mVpBanner2.setAdapter(adapter2);
        if(mImageLength == 1){
            mVpBanner2.setCurrentItem(0);
        }else{
            mVpBanner2.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImageLength);
        }
    }

    private void initAutoScroll() {
        if(mImageLength <= 1){
            return;
        }
        mAutoScrollDelegate1 = new AutoScrollDelegate4();
        mAutoScrollDelegate1.setViewPager(mVpBanner1);
        mAutoScrollDelegate1.setIsAutoPlay(true);
        mAutoScrollDelegate1.setAutoPlayDuration(3000);
        mAutoScrollDelegate1.setAnimDuration(1200);
        mAutoScrollDelegate1.addToActivity(this, "autoScroll");

        mAutoScrollDelegate2 = new AutoScrollDelegate4();
        mAutoScrollDelegate2.setViewPager(mVpBanner2);
        mAutoScrollDelegate2.setIsAutoPlay(true);
        mAutoScrollDelegate2.setAutoPlayDuration(4000);
        mAutoScrollDelegate2.setAnimDuration(1200);
        mAutoScrollDelegate2.addToActivity(this, "autoScroll2");
    }

    private void initIndicators(){
        if(mImageLength <= 1){
            return;
        }
        mIndicatorDelegate1 = new IndicatorDelegate16();
        mIndicatorDelegate1.setViewPager(mVpBanner1);
        mIndicatorDelegate1.setLayoutBanner(mLayoutBanner1);
        mIndicatorDelegate1.setIndicatorPosition(IndicatorDelegate16.Position.CENTER_BOTTOM);
        mIndicatorDelegate1.setIndicatorsLayoutXMargin(25);
        mIndicatorDelegate1.setIndicatorsLayoutYMargin(25);
        mIndicatorDelegate1.setIndicatorsLayoutPadding(10);
        mIndicatorDelegate1.setFloated(false);
        mIndicatorDelegate1.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mIndicatorDelegate1.setIndicatorSpace(20);
        mIndicatorDelegate1.initIndicators(mImageLength);
        mIndicatorDelegate1.addToActivity(this, "indicator");

        mIndicatorDelegate2 = new IndicatorDelegate16();
        mIndicatorDelegate2.setViewPager(mVpBanner2);
        mIndicatorDelegate2.setLayoutBanner(mLayoutBanner2);
        mIndicatorDelegate2.setIndicatorPosition(IndicatorDelegate16.Position.RIGHT_BOTTOM);
        mIndicatorDelegate2.setIndicatorsLayoutXMargin(25);
        mIndicatorDelegate2.setIndicatorsLayoutYMargin(25);
        mIndicatorDelegate2.setIndicatorsLayoutPadding(10);
        mIndicatorDelegate2.setFloated(true);
        mIndicatorDelegate2.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mIndicatorDelegate2.setIndicatorSpace(20);
        mIndicatorDelegate2.initIndicators(mImageLength);
        mIndicatorDelegate2.addToActivity(this, "indicator2");
    }

    //todo 粗暴方案，有bug
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mAutoScrollDelegate1 == null){
            return super.dispatchTouchEvent(ev);
        }
        if(mAutoScrollDelegate2 == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mAutoScrollDelegate1.stopAutoPlay();
                mAutoScrollDelegate2.stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mAutoScrollDelegate1.startAutoPlay();
                mAutoScrollDelegate2.startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}