package com.jc.flora.apps.component.request.nao;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class GetMobileInfoApi {

    private static boolean MOCK = false;

    private RequestCallback<String> mCallBack;
    private MobileInfoURLData mUrlData = new MobileInfoURLData();

    public GetMobileInfoApi(RequestCallback<String> callBack) {
        mCallBack = callBack;
    }

    public void sendRequest(String mobileCode) {
        sendRequest(mobileCode, true);
    }

    public void sendRequest(String mobileCode, boolean useCache) {
        if(MOCK){
            doMock();
        }else {
            if(!useCache){
                mUrlData.expires = 0;
            }
            ArrayList<RequestParameter> params = new ArrayList<>();
            params.add(new RequestParameter("mobileCode", mobileCode));
            RequestManager.getInstance().createAndAddRequest(mUrlData, params, mCallBack);
        }
    }

    private void doMock() {
        if (mCallBack != null){
            mCallBack.onSuccess("soap请求返回测试数据");
        }
    }

    private static class MobileInfoURLData extends URLData<String>{

        public MobileInfoURLData() {
            super("getMobileCodeInfo", 0, "soap", "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx", String.class);
            soapNameSpace = "http://WebXml.com.cn/";
            soapMethodName = "getMobileCodeInfo";
        }

        @Override
        public String parseResponse(SoapObject soapObject) {
            return soapObject.getProperty(0).toString();
        }
    }

}