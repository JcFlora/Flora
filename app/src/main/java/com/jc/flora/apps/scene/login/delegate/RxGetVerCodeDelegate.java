package com.jc.flora.apps.scene.login.delegate;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Response;
import com.jakewharton.rxbinding2.view.RxView;
import com.jc.flora.R;
import com.jc.flora.apps.component.request.volley.L;
import com.jc.flora.apps.scene.login.api.GetVerCodeMockApi;
import com.jc.flora.apps.scene.login.api.GetVerCodeResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by Samurai on 2017/8/28.
 */
public class RxGetVerCodeDelegate {

    /** 验证码按钮文字：验证码 */
    private static final String BTN_VER_CODE_TEXT = "验证码";
    /** 按钮可用状态背景 */
    private static final int BG_RES_ENABLED = R.drawable.app_bg_btn_accent;
    /** 按钮不可用状态背景 */
    private static final int BG_RES_UNABLE = R.drawable.app_bg_btn_unable;
    /** 验证码获取倒计时时长，单位秒 */
    private static final long VER_CODE_COUNT_DOWN_TIME = 30;
    /** 验证码获取倒计时刷新间隔，单位秒 */
    private static final long VER_CODE_COUNT_DOWN_INTERVAL = 1;

    private AppCompatActivity mActivity;
    /** 验证码按钮 */
    private TextView mBtnVerCode;
    /** 手机号填写业务管理 */
    private PhoneNumberInputDelegate mPhoneNumberInputDelegate;
    /** 验证码获取成功后的监听 */
    private OnGetVerCodeSuccessListener mGetVerCodeListener;

    // 验证码主题
    private Observable<Object> mVerCodeObservable;
    private Observable<Long> mCountDownObservable;

    public RxGetVerCodeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 配置验证码按钮
     *
     * @param btnVerCode 验证码按钮
     */
    public void setBtnVerCode(TextView btnVerCode) {
        mBtnVerCode = btnVerCode;
    }

    /**
     * 配置手机号填写业务管理
     *
     * @param delegate 手机号填写业务管理
     */
    public void setPhoneNumberInputDelegate(PhoneNumberInputDelegate delegate) {
        mPhoneNumberInputDelegate = delegate;
    }

    public void setGetVerCodeListener(OnGetVerCodeSuccessListener l) {
        mGetVerCodeListener = l;
    }

    /** 验证码业务的准备工作 */
    public void ready() {
        if (mBtnVerCode == null || mPhoneNumberInputDelegate == null)
            return;
        // 设置验证码按钮的点击事件
        mVerCodeObservable = RxView.clicks(mBtnVerCode)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(@NonNull Object o) throws Exception {
                        // 过滤掉手机号非法情况
                        return mPhoneNumberInputDelegate.isPhoneNumberOk();
                    }
                })
                // 倒计时时常内，只有第一次点击会触发后面的订阅事件
                .throttleFirst(VER_CODE_COUNT_DOWN_TIME, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        // 获取验证码
                        getVerCode();
                    }
                });
        mVerCodeObservable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                // 启动倒计时主题
                startCountDown();
            }
        });
    }

    /** 获取验证码 */
    private void getVerCode() {
        final String phoneNumber = mPhoneNumberInputDelegate.getPhoneNumber();
        // 按钮设置为不可用
        setBtnEnabled(false);
        // 发起获取验证码的请求
        requestGetVerCode(phoneNumber);
    }

    /** 启动倒计时主题 */
    private void startCountDown() {
        // 按单位时间分发事件
        mCountDownObservable = Observable.interval(VER_CODE_COUNT_DOWN_INTERVAL, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                // 设置保持时长
                .take(VER_CODE_COUNT_DOWN_TIME);
        // 订阅事件：倒计时反馈事件
        mCountDownObservable.subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        // 每隔1秒刷新一次验证码按钮上的时间
                        mBtnVerCode.setText((VER_CODE_COUNT_DOWN_TIME - aLong - 1) + "秒");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        // 倒计时结束，按钮设置为可用
                        setBtnEnabled(true);
                    }
                });
    }

    /**
     * 设置按钮是否可用
     *
     * @param enabled 是否可用
     */
    private void setBtnEnabled(boolean enabled) {
        mBtnVerCode.setText(enabled ? BTN_VER_CODE_TEXT : VER_CODE_COUNT_DOWN_TIME + "秒");
        mBtnVerCode.setEnabled(enabled);
        mBtnVerCode.setBackgroundResource(enabled ? BG_RES_ENABLED : BG_RES_UNABLE);
    }

    /**
     * 发起请求：获取验证码
     *
     * @param phoneNumber 手机号
     */
    private void requestGetVerCode(String phoneNumber) {
        new GetVerCodeMockApi(mActivity, new Response.Listener<GetVerCodeResponse>() {
            @Override
            public void onResponse(GetVerCodeResponse response) {
                onGetVerCodeResponse(response);
            }
        }).sendRequest(phoneNumber);
    }

    /**
     * 获取验证码成功后的回调
     *
     * @param response 响应实体
     */
    private void onGetVerCodeResponse(GetVerCodeResponse response) {
        if (response == null)
            return;
        L.w("getVerCode", response.msg);
        // 如果有回调事件，响应回调事件
        if (response.success && mGetVerCodeListener != null)
            mGetVerCodeListener.onGetVerCodeSuccess(response);
    }

    /** 验证码逻辑停止，在Activity的onDestroy（）方法中调用 */
    public void stop() {
        // 取消倒计时观测
        mVerCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        if(mCountDownObservable != null){
            mCountDownObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        }
    }

    /** 验证码获取成功后的监听 */
    public interface OnGetVerCodeSuccessListener {
        /** 验证码获取成功后的监听 */
        void onGetVerCodeSuccess(GetVerCodeResponse response);
    }

}