package com.jc.flora.apps.component.upgrade.api;

/**
 * Created by shijincheng on 2017/3/10.
 */
public class CheckVersionUpgradeApi {

    private Listener<AppUpgradeInfo> mListener;
    private ErrorListener mErrorListener;

    public CheckVersionUpgradeApi(Listener<AppUpgradeInfo> listener, ErrorListener errorListener) {
        mListener = listener;
        mErrorListener = errorListener;
    }

    public void sendRequest(){
        if(mListener != null){
            mListener.onResponse(AppUpgradeInfo.createTestInfo());
        }
    }

    public interface Listener<T>{
        void onResponse(T response);
    }

    public interface ErrorListener{
        void onErrorResponse();
    }

}