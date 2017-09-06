package com.jc.flora.apps.scene.payment.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.scene.payment.delegate.PaymentTypeDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.list.widget.SlipsView;

/**
 * Created by Samurai on 2017/9/5.
 */
public class Payment1Activity extends AppCompatActivity {

    private SlipsView mSlips;
    private PaymentTypeDelegate mPaymentTypeDelegate;
    private AppCompatButton mBtnGotoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择支付方式");
        setContentView(R.layout.activity_payment1);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews() {
        mSlips = (SlipsView)findViewById(R.id.slips);
        mBtnGotoPay = (AppCompatButton) findViewById(R.id.btn_goto_pay);
    }

    private void initViews(){
        mBtnGotoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPay();
            }
        });
    }

    private void initDelegate(){
        mPaymentTypeDelegate = new PaymentTypeDelegate(this, mSlips);
        mPaymentTypeDelegate.init();
    }

    private void gotoPay() {
        String s;
        switch (mPaymentTypeDelegate.getPaymentType()) {
            case ZFB:
                s = "使用支付宝支付";
                break;
            case HUA_BEI:
                s = "使用花呗支付";
                break;
            case QQ:
                s = "使用QQ支付";
                break;
            case WEI_XIN:
                s = "使用微信支付";
                break;
            default:
                s = "暂不支持此支付方式";
                break;
        }
        ToastDelegate.show(this, s);
    }

}