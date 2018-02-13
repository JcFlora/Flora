package com.jc.flora.apps.component.request.volley;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Api基类
 * Created by shijincheng on 2017/3/18.
 */
public abstract class BaseApi3<Resp extends BaseResponse> implements Response.Listener<Resp>, Response.ErrorListener {

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
    public BaseApi3(AppCompatActivity activity, Response.Listener<Resp> l) {
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
    public BaseApi3(AppCompatActivity activity, Response.Listener<Resp> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    /** 发送请求 */
    protected void sendRequest() {
        // 将this作为请求响应成功的监听，由this处理后再回调mListener
        RequestManager.getInstance(mActivity).createAndAddRequest(Request.Method.GET, getHost() + getAction(),
                this, mErrorListener, getClass().getSimpleName(), getRespClass());
    }

//    protected abstract int getMethod();

    /** 设置Host */
    protected abstract String getHost();

    /** 设置Action */
    protected abstract String getAction();

    /** 设置返回数据格式 */
    protected Class<Resp> getRespClass(){
        // Type是Java中所有类型的公共高级接口
        // ParameterizedType是参数化类型，即泛型
        ParameterizedType genType = (ParameterizedType) getClass().getGenericSuperclass();
        // getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Type[] params = genType.getActualTypeArguments();
        // 泛型只有一个的时候，第1个即为泛型
        return (Class<Resp>) params[0];
    }

    @Override
    public void onResponse(Resp response) {
        // 如果response的error为true，做统一拦截处理
        if (response.error) {
            if (mActivity != null) {
//                // 关闭Loading框
//                mActivity.hideLoadingDialog();
                // 弹出错误提示
                ToastDelegate.show(mActivity,"获取数据出错，请稍后再试");
            }
        }else if(mListener != null){
            mListener.onResponse(response);
        }
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
