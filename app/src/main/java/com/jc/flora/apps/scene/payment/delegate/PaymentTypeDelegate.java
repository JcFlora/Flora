package com.jc.flora.apps.scene.payment.delegate;

import android.content.Context;

import com.jc.flora.R;
import com.jc.flora.apps.ui.list.adapter.RadioAdapter;
import com.jc.flora.apps.ui.list.model.Slip3;
import com.jc.flora.apps.ui.list.widget.SlipsView;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/9/5.
 */
public class PaymentTypeDelegate {

    private Context mContext;

    private SlipsView mSlips;
    private ArrayList<Slip3> mSlipList;
    private RadioAdapter mAdapter;

    public PaymentTypeDelegate(Context context, SlipsView slips) {
        mContext = context;
        mSlips = slips;
    }

    public void init(){
        initData();
        initViews();
    }

    public PaymentType getPaymentType(){
        return PaymentType.values()[mAdapter.getSelectedIndex()];
    }

    private void initData() {
        mSlipList = new ArrayList<>(4);
        addSlipRadio(mSlipList, "支付宝", "使用支付宝支付", R.mipmap.ic_payment_ali);
        addSlipRadio(mSlipList, "花呗支付", "使用花呗支付", R.mipmap.ic_payment_huabei);
        addSlipRadio(mSlipList, "QQ支付", "使用QQ支付", R.mipmap.ic_payment_qq);
        addSlipRadio(mSlipList, "微信支付", "使用微信支付", R.mipmap.ic_payment_wexin);
    }

    private void initViews() {
        mAdapter = new RadioAdapter(mContext, mSlipList);
        mSlips.setAdapter(mAdapter);
    }

    private void addSlipRadio(ArrayList<Slip3> list, String title, String subTitle, int iconResId) {
        Slip3 slip = new Slip3();
        slip.iconResId = iconResId;
        slip.title = title;
        slip.subTitle = subTitle;
        list.add(slip);
    }

    public enum PaymentType{
        ZFB,HUA_BEI,QQ,WEI_XIN
    }

}