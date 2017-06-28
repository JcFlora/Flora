package com.jc.flora.apps.component.request.volley;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Api基类
 * Created by shijincheng on 2017/3/18.
 */
public abstract class BaseApi<Resp> implements Response.ErrorListener {

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<Resp> mListener;
    /** 返回失败的响应 */
    private Response.ErrorListener mErrorListener;

    /**
     * Api基类
     *
     * @param activity 当前界面
     * @param l        返回成功的响应
     */
    public BaseApi(AppCompatActivity activity, Response.Listener<Resp> l) {
        mActivity = activity;
        mListener = l;
        mErrorListener = this;
    }

    /**
     * Api基类
     *
     * @param activity 当前界面
     * @param l  返回成功的响应
     * @param el 返回失败的响应
     */
    public BaseApi(AppCompatActivity activity, Response.Listener<Resp> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    /** 发送请求 */
    protected void sendRequest() {
        RequestManager.getInstance(mActivity).createAndAddRequest(Request.Method.GET, getHost() + getAction(),
                mListener, mErrorListener, getClass().getSimpleName(), getRespClass());
    }

//    protected abstract int getMethod();

    /** 设置Host */
    protected abstract String getHost();

    /** 设置Action */
    protected abstract String getAction();

    /** 设置返回数据格式 */
    protected abstract Class<Resp> getRespClass();

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
