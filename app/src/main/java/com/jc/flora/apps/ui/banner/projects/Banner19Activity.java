package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.IndicatorDelegate16;
import com.jc.flora.apps.ui.banner.transformer.TransitionEffect;
import com.jc.flora.apps.ui.banner.widget.BannerView19;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/8/17.
 */
public class Banner19Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    private BannerView19 mBv1,mBv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装成BannerView");
        setContentView(R.layout.activity_banner19);
        findViews();
        initBanner1();
        initBanner2();
    }

    private void findViews() {
        mBv1 = (BannerView19) findViewById(R.id.bv1);
        mBv2 = (BannerView19) findViewById(R.id.bv2);
    }

    private void initBanner1(){
        mBv1.setImageUris(IMAGE_URIS);
        mBv1.setIsAutoPlay(true);
        mBv1.setAutoPlayDuration(3000);
        mBv1.setAnimDuration(1800);
        mBv1.setIndicatorPosition(IndicatorDelegate16.Position.CENTER_BOTTOM);
        mBv1.setIndicatorsLayoutXMargin(25);
        mBv1.setIndicatorsLayoutYMargin(25);
        mBv1.setIndicatorsLayoutPadding(10);
        mBv1.setIndicatorFloated(false);
        mBv1.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv1.setIndicatorSpace(20);
        mBv1.setOnBannerItemClickListener(new BannerView19.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner19Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        mBv1.start();
        mBv1.setPageTransformer(TransitionEffect.Flip);
    }

    private void initBanner2(){
        mBv2.setImageUris(IMAGE_URIS);
        mBv2.setIsAutoPlay(true);
        mBv2.setAutoPlayDuration(4000);
        mBv2.setAnimDuration(1200);
        mBv2.setIndicatorPosition(IndicatorDelegate16.Position.CENTER_BOTTOM);
        mBv2.setIndicatorsLayoutXMargin(25);
        mBv2.setIndicatorsLayoutYMargin(25);
        mBv2.setIndicatorsLayoutPadding(10);
        mBv2.setIndicatorFloated(true);
        mBv2.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv2.setIndicatorSpace(20);
        mBv2.setOnBannerItemClickListener(new BannerView19.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner19Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        mBv2.start();
        mBv2.setPageTransformer(TransitionEffect.Accordion);
    }

}