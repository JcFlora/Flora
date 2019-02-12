package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.BannerDelegate17;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate16;
import com.jc.flora.apps.ui.banner.widget.BannerViewPager;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/8/16.
 */
public class Banner17Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    private RelativeLayout mLayoutBanner1, mLayoutBanner2;
    private BannerViewPager mVpBanner1, mVpBanner2;
    private BannerDelegate17 mDelegate1, mDelegate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装成BannerDelegate");
        setContentView(R.layout.activity_banner16);
        findViews();
        initDelegate1();
        initDelegate2();
    }

    private void findViews() {
        mLayoutBanner1 = (RelativeLayout) findViewById(R.id.layout_banner1);
        mVpBanner1 = (BannerViewPager) findViewById(R.id.vp_banner1);
        mLayoutBanner2 = (RelativeLayout) findViewById(R.id.layout_banner2);
        mVpBanner2 = (BannerViewPager) findViewById(R.id.vp_banner2);
    }

    private void initDelegate1(){
        mDelegate1 = new BannerDelegate17();
        mDelegate1.setImageUris(IMAGE_URIS);
        mDelegate1.setLayoutBanner(mLayoutBanner1);
        mDelegate1.setViewPager(mVpBanner1);
        mDelegate1.setIsAutoPlay(true);
        mDelegate1.setAutoPlayDuration(3000);
        mDelegate1.setAnimDuration(1200);
        mDelegate1.setIndicatorPosition(IndicatorDelegate16.Position.CENTER_BOTTOM);
        mDelegate1.setIndicatorsLayoutXMargin(25);
        mDelegate1.setIndicatorsLayoutYMargin(25);
        mDelegate1.setIndicatorsLayoutPadding(10);
        mDelegate1.setIndicatorFloated(false);
        mDelegate1.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mDelegate1.setIndicatorSpace(20);
        mDelegate1.setOnBannerItemClickListener(new BannerDelegate17.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner17Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        mDelegate1.addToActivity(this, "banner");
    }

    private void initDelegate2(){
        mDelegate2 = new BannerDelegate17();
        mDelegate2.setImageUris(IMAGE_URIS);
        mDelegate2.setLayoutBanner(mLayoutBanner2);
        mDelegate2.setViewPager(mVpBanner2);
        mDelegate2.setIsAutoPlay(true);
        mDelegate2.setAutoPlayDuration(4000);
        mDelegate2.setAnimDuration(1200);
        mDelegate2.setIndicatorPosition(IndicatorDelegate16.Position.CENTER_BOTTOM);
        mDelegate2.setIndicatorsLayoutXMargin(25);
        mDelegate2.setIndicatorsLayoutYMargin(25);
        mDelegate2.setIndicatorsLayoutPadding(10);
        mDelegate2.setIndicatorFloated(true);
        mDelegate2.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mDelegate2.setIndicatorSpace(20);
        mDelegate2.setOnBannerItemClickListener(new BannerDelegate17.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner17Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        mDelegate2.addToActivity(this, "banner2");
    }

    //todo 粗暴方案，有bug
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mDelegate1 == null){
            return super.dispatchTouchEvent(ev);
        }
        if(mDelegate2 == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDelegate1.stopAutoPlay();
                mDelegate2.stopAutoPlay();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDelegate1.startAutoPlay();
                mDelegate2.startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}