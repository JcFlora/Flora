package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.AutoScrollDelegate3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Banner3Activity extends AppCompatActivity {

    private ViewPager mVpBanner;
    private List<View> mViews;
    private AutoScrollDelegate3 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自动翻页");
        setContentView(R.layout.activity_banner1);
        findViews();
        initData();
        setAdapter();
        initAutoScroll();
    }

    private void findViews() {
        mVpBanner = (ViewPager) findViewById(R.id.vp_banner);
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
        mDelegate = new AutoScrollDelegate3();
        mDelegate.setViewPager(mVpBanner);
        mDelegate.setIsAutoPlay(true);
        mDelegate.setAutoPlayDuration(3000);
        mDelegate.addToActivity(this, "autoScroll");
    }

}
