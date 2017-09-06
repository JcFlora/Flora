package com.jc.flora.apps.scene.payment.delegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jc.flora.apps.scene.payment.alipay.Alice;
import com.jc.flora.apps.scene.payment.alipay.PayResult;
import com.jc.flora.apps.scene.payment.api.GetOrderInfoMockApi;
import com.jc.flora.apps.ui.banner.ec.BaseApi;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Samurai on 2017/9/6.
 */
public class AliPayDelegate5 {

    private static final int SDK_PAY_FLAG = 1;

    private AppCompatActivity mActivity;

    public AliPayDelegate5(AppCompatActivity activity) {
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
        new GetOrderInfoMockApi(mActivity)
                // 第2步：从服务端获取OrderInfo(模拟)
                .getOrderInfo("我是测试数据", "测试商品", "0.01")
                .observeOn(Schedulers.io())
                .map(new Function<String, PayResult>() {
                    @Override
                    public PayResult apply(@NonNull String s) throws Exception {
                        // 第3步：调用SDK异步发起支付请求
                        return requestPay(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseApi.ObserverAdapter<PayResult>() {
                    @Override
                    public void onNext(@NonNull PayResult payResult) {
                        // 第4步：支付完成后的回调
                        onPayCompleted(payResult);
                    }
                });
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
     * 发起支付请求（同步）
     * @param orderInfo
     */
    private PayResult requestPay(String orderInfo){
        return new PayResult(new PayTask(mActivity).payV2(orderInfo, true));
    }

    /**
     * 支付完成后的回调
     * @param payResult
     */
    @SuppressWarnings("unchecked")
    private void onPayCompleted(PayResult payResult){
        // 同步返回需要验证的信息
        String resultInfo = payResult.getResult();
        /** 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。*/
        String resultStatus = payResult.getResultStatus();
        // 判断resultStatus 为9000则代表支付成功
        boolean isSuccess = TextUtils.equals(resultStatus, Alice.PAY_SUCCESS);
        String toast = isSuccess ? "支付成功" : "支付失败";
        ToastDelegate.show(mActivity, toast);
    }

}