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
import com.jc.flora.apps.ui.banner.delegate.CursorIndicatorDelegate15;
import com.jc.flora.apps.ui.banner.widget.BannerViewPager;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner15Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    private int mImageLength;

    private RelativeLayout mLayoutBanner;
    private BannerViewPager mVpBanner;
    private List<View> mViews;
    private AutoScrollDelegate4 mAutoScrollDelegate;
    private CursorIndicatorDelegate15 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("处理浮动式指示器边界问题");
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
        // 重点说明：ViewPager有预加载缓存，所以数量为2个以下时，必须手动添加多个
        // 同时，ViewPager向右和向左滑动时，destroyItem和instantiateItem的执行顺序不一样，
        // 数量为3个时，向左滑动会先add后remove，导致报错，所以3个需添加到6个保证左滑不报错
        int[] lt3lengths = {0, 4, 4, 6};
        int falseLength = (mImageLength <= 3) ? (lt3lengths[mImageLength]) : mImageLength;
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
                    ToastDelegate.show(Banner15Activity.this, "banner" + j);
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
        mIndicatorDelegate = new CursorIndicatorDelegate15();
        mIndicatorDelegate.setViewPager(mVpBanner);
        mIndicatorDelegate.setLayoutBanner(mLayoutBanner);
        mIndicatorDelegate.setIndicatorPosition(CursorIndicatorDelegate15.Position.CENTER_BOTTOM);
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