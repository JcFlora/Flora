package com.jc.flora.apps.scene.payment.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.scene.payment.delegate.AliPayDelegate4;
import com.jc.flora.apps.scene.payment.delegate.PaymentTypeDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.list.widget.SlipsView;

/**
 * 支付宝sdk小Bug:加载框在当前主题下显示宽度为match_parent
 * 若要修复，需继承FragmentActivity，并设置主题为NoTitleBar
 * Created by Samurai on 2017/9/6.
 */
public class Payment4Activity extends AppCompatActivity {

    private SlipsView mSlips;
    private PaymentTypeDelegate mPaymentTypeDelegate;
    private AliPayDelegate4 mAliPayDelegate;
    private AppCompatButton mBtnGotoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("模拟服务端获取OrderInfo");
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
        mAliPayDelegate = new AliPayDelegate4(this);
    }

    private void gotoPay() {
        switch (mPaymentTypeDelegate.getPaymentType()) {
            case ZFB:
                gotoZfb();
                break;
            default:
                ToastDelegate.show(this, "暂不支持此支付方式");
                break;
        }
    }

    private void gotoZfb(){
        mAliPayDelegate.pay();
    }

}