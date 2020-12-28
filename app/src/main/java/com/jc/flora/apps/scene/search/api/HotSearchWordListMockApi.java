package com.jc.flora.apps.scene.search.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/5/19.
 */
public class HotSearchWordListMockApi implements Response.ErrorListener{

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<List<String>> mListener;
    /** 返回失败的响应 */
    private Response.ErrorListener mErrorListener;

    public HotSearchWordListMockApi(AppCompatActivity activity, Response.Listener<List<String>> l) {
        mActivity = activity;
        mListener = l;
    }

    public HotSearchWordListMockApi(AppCompatActivity activity, Response.Listener<List<String>> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    public void sendRequest(){
        //Mock
        ArrayList<String> mockList = new ArrayList<>();
        mockList.add("吴宣仪");
        mockList.add("进击的巨人");
        mockList.add("百香果女孩");
        mockList.add("特朗普");
        mockList.add("新冠肺炎");
        mockList.add("幼儿园纳入义务教育");
        mockList.add("我和我的家乡");
        mListener.onResponse(mockList);
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
