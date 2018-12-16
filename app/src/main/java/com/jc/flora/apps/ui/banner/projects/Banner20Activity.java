package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.transformer.TransitionEffect;
import com.jc.flora.apps.ui.banner.widget.BannerView20;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/8/21.
 */
public class Banner20Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
                    "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private BannerView20 mBv1, mBv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("改变Delegate方式");
        setContentView(R.layout.activity_banner20);
        findViews();
        initBanner1();
        initBanner2();
    }

    private void findViews() {
        mBv1 = (BannerView20) findViewById(R.id.bv1);
        mBv2 = (BannerView20) findViewById(R.id.bv2);
    }

    private void initBanner1(){
        mBv1.setImageUris(IMAGE_URIS);
        mBv1.setIsAutoPlay(true);
        mBv1.setAutoPlayDuration(3000);
        mBv1.setAnimDuration(1800);
        mBv1.setIndicatorPosition(BannerView20.Position.CENTER_BOTTOM);
        mBv1.setIndicatorsLayoutXMargin(25);
        mBv1.setIndicatorsLayoutYMargin(25);
        mBv1.setIndicatorsLayoutPadding(10);
        mBv1.setIndicatorFloated(false);
        mBv1.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv1.setIndicatorSpace(20);
        mBv1.setOnBannerItemClickListener(new BannerView20.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner20Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        //修改后setPageTransformer要在start前调用
        mBv1.setPageTransformer(TransitionEffect.Flip);
        mBv1.start();
    }

    private void initBanner2(){
        mBv2.setImageUris(IMAGE_URIS);
        mBv2.setIsAutoPlay(true);
        mBv2.setAutoPlayDuration(4000);
        mBv2.setAnimDuration(1200);
        mBv2.setIndicatorPosition(BannerView20.Position.CENTER_BOTTOM);
        mBv2.setIndicatorsLayoutXMargin(25);
        mBv2.setIndicatorsLayoutYMargin(25);
        mBv2.setIndicatorsLayoutPadding(10);
        mBv2.setIndicatorFloated(true);
        mBv2.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv2.setIndicatorSpace(20);
        mBv2.setOnBannerItemClickListener(new BannerView20.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner20Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        //修改后setPageTransformer要在start前调用
        mBv2.setPageTransformer(TransitionEffect.Accordion);
        mBv2.start();
    }

}