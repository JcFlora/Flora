package com.jc.flora.apps.ui.banner.delegate;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.ec.BannerInfo;
import com.jc.flora.apps.ui.banner.transformer.TransitionEffect;
import com.jc.flora.apps.ui.banner.widget.BannerView21;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/8/22.
 */
public class BannerDelegate22 {

    private AppCompatActivity mActivity;
    private BannerView21 mBannerView;

    private ArrayList<BannerInfo> mBannerList;

    public BannerDelegate22(AppCompatActivity activity,BannerView21 bv) {
        mActivity = activity;
        mBannerView = bv;
        init();
    }

    private void init(){
        mBannerView.setIsAutoPlay(true);
        mBannerView.setAutoPlayDuration(3000);
        mBannerView.setAnimDuration(1800);
        mBannerView.setIndicatorPosition(BannerView21.Position.CENTER_BOTTOM);
        mBannerView.setIndicatorsLayoutXMargin(25);
        mBannerView.setIndicatorsLayoutYMargin(25);
        mBannerView.setIndicatorsLayoutPadding(10);
        mBannerView.setIndicatorFloated(false);
        mBannerView.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBannerView.setIndicatorSpace(20);
        mBannerView.setOnBannerItemClickListener(new BannerView21.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(mActivity, mBannerList.get(position).subject);
            }
        });
        //修改后setPageTransformer要在start前调用
        mBannerView.setPageTransformer(TransitionEffect.Accordion);
    }

    public void reload(ArrayList<BannerInfo> bannerList){
        mBannerList = bannerList;
        // 添加数据投放器，修改数据投放方式，支持任意方式加载数据
        mBannerView.setBannerDataDelivery(new BannerView21.BannerDataDelivery() {
            @Override
            public int getCount() {
                return mBannerList.size();
            }
            @Override
            public void deliverImage(ImageView imageView, int position) {
                // 注意这里面使用了dontAnimate()和dontTransform()，可以防止在ViewPager里出现闪烁
                Glide.with(mActivity).load(mBannerList.get(position).imgUrl).apply(new RequestOptions().dontAnimate().dontTransform()).into(imageView);
            }
        });
        mBannerView.reload();
    }

}
