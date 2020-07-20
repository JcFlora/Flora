package com.jc.flora.apps.scene.payment.api;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.scene.payment.alipay.OrderInfoUtil2_0;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shijincheng on 2017/9/6.
 */
public class GetOrderInfoMockApi implements Response.ErrorListener{

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<String> mListener;
    /** 返回失败的响应 */
    private Response.ErrorListener mErrorListener;

    public GetOrderInfoMockApi(AppCompatActivity activity) {
        mActivity = activity;
    }

    public GetOrderInfoMockApi(AppCompatActivity activity, Response.Listener<String> l) {
        mActivity = activity;
        mListener = l;
    }

    public GetOrderInfoMockApi(AppCompatActivity activity, Response.Listener<String> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    public void sendRequest(String body, String subject, String amount) {
        mListener.onResponse(OrderInfoUtil2_0.createOrderInfo(body, subject, amount, null));
    }

    public Observable<String> getOrderInfo(final String body, final String subject, final String amount) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String orderInfo = OrderInfoUtil2_0.createOrderInfo(body, subject, amount, null);
                e.onNext(orderInfo);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /** 返回失败的默认响应 */
    public void onErrorResponse(VolleyError error) {
        if (mActivity != null) {
//            // 关闭Loading框
//            mActivity.hideLoadingDialog();
            // 弹出错误提示
            ToastDelegate.show(mActivity,"获取数据出错，请稍后再试");
        }
    }

}
