package com.jc.flora.apps.component.request.soap;

import android.content.ContentValues;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

class SoapMethod {

    /** soap基址 */
    private String mUrl;
    /** soap命名空间 */
    private String mSpace;
    /** 方法名 */
    private String mMethodName;
    /** 参数列表 */
    private ContentValues mParams;

    SoapMethod(String url, String space, String methodName, ContentValues params) {
        mUrl = url;
        mSpace = space;
        mMethodName = methodName;
        mParams = params;
    }

    SoapObject doSoap() {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(mSpace, mMethodName);
        if (mParams != null && mParams.size() > 0) {
            for (Map.Entry<String, Object> param : mParams.valueSet()) {
                String key = param.getKey();
                String val = param.getValue().toString();
                soapObject.addProperty(key, val);
            }
        }
        envelope.bodyOut = soapObject;
        envelope.encodingStyle = "UTF-8";
        envelope.dotNet = true;
        Log.e("out", envelope.bodyOut.toString());
        HttpTransportSE ht = new HttpTransportSE(mUrl);
        ht.debug = true;
        try {
            ht.call(mSpace + mMethodName, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.e("in", envelope.bodyIn.toString());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}