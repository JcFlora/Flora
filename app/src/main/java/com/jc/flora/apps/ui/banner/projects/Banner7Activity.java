package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/4/30.
 */
public class Banner7Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    private ViewPager mVpBanner;
    private LinearLayout mLayoutBannerIndicators;
    private List<View> mViews;
    private AutoScrollDelegate4 mDelegate;

    private ImageView[] mIvIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("加载网络图片");
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
        mDelegate = new AutoScrollDelegate4();
        mDelegate.setViewPager(mVpBanner);
        mDelegate.setIsAutoPlay(true);
        mDelegate.setAutoPlayDuration(3000);
        mDelegate.setAnimDuration(1200);
        mDelegate.addToActivity(this, "autoScroll");
    }

    private void initIndicators(){
        final int length = IMAGE_URIS.length;
        mIvIndicators = new ImageView[length];
        for (int i = 0; i < length; i++) {
            mIvIndicators[i] = new ImageView(this);
            mIvIndicators[i].setImageResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            mLayoutBannerIndicators.addView(mIvIndicators[i], params);
            if (i != (length - 1)) {
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
