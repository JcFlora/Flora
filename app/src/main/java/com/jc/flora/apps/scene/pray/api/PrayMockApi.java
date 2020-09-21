package com.jc.flora.apps.scene.pray.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/21.
 */
public class PrayMockApi implements Response.ErrorListener{

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<PrayResponse> mListener;

    public PrayMockApi(AppCompatActivity activity, Response.Listener<PrayResponse> l) {
        mActivity = activity;
        mListener = l;
    }

    public void sendOpenGrayFilterRequest(){
        //Mock
        mListener.onResponse(new PrayResponse(true, "开启置灰"));
    }

    public void sendCloseGrayFilterRequest(){
        //Mock
        mListener.onResponse(new PrayResponse(false, "关闭置灰"));
    }

    /** 返回失败的默认响应 */
    @Override
    public void onErrorResponse(VolleyError error) {
        if (mActivity != null) {
//            // 关闭Loading框
//            mActivity.hideLoadingDialog();
            // 弹出错误提示
            ToastDelegate.show(mActivity,"获取数据出错，请稍后再试");
        }
    }

}
