package com.jc.flora.apps.scene.payment.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jc.flora.apps.scene.payment.alipay.Alice;
import com.jc.flora.apps.scene.payment.alipay.OrderInfoUtil2_0;

/**
 * Created by Samurai on 2017/9/5.
 */
public class AliPayDelegate2 {

    private AppCompatActivity mActivity;

    public AliPayDelegate2(AppCompatActivity activity) {
        mActivity = activity;
        // 沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    /**
     * 支付
     */
    public void pay() {
        // 第1步：检查参数是否配置齐全
        if (!checkConfig()) {
            return;
        }
        // 第2步：创建OrderInfo
        String orderInfo = OrderInfoUtil2_0.createOrderInfo("我是测试数据", "测试商品", "0.01", null);
        // 第3步：调用SDK异步发起支付请求
        requestPayAsync(orderInfo);
    }

    /**
     * 检查参数是否配置齐全
     * @return 是否配置齐全
     */
    private boolean checkConfig() {
        if (TextUtils.isEmpty(Alice.APP_ID) || TextUtils.isEmpty(Alice.RSA2_PRIVATE)) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("警告")
                    .setMessage("需要配置APP_ID | RSA2_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            mActivity.finish();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    /**
     * 调用SDK异步发起支付请求
     * @param orderInfo
     */
    private void requestPayAsync(final String orderInfo) {
        new Thread() {
            @Override
            public void run() {
                requestPay(orderInfo);
            }
        }.start();
    }

    /**
     * 发起支付请求（同步）
     * @param orderInfo
     */
    private void requestPay(String orderInfo){
        new PayTask(mActivity).payV2(orderInfo, true);
    }

}