package com.jc.flora.apps.component.request.retrofit.soap.ksoap2;

import android.util.Log;

import com.jc.flora.apps.component.request.retrofit.soap.serialization.MarshalBase64;
import com.jc.flora.apps.component.request.retrofit.soap.serialization.SoapObject;
import com.jc.flora.apps.component.request.retrofit.soap.serialization.SoapSerializationEnvelope;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 帮助类：转换请求头，请求体。
 */
public class KSoap2RetrofitHelper {

    private String TAG = getClass().getSimpleName();

    private static KSoap2RetrofitHelper kSoap2RetrofitHelper;

    /**
     * 协议版本，默认110
     */
    private int envelopeversion = SoapEnvelope.VER11;

    /**
     * 设置版本号
     *
     * @param envelopeversion
     * @return
     */
    public KSoap2RetrofitHelper setEnvelopeversion(int envelopeversion) {
        this.envelopeversion = envelopeversion;
        return this;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public int getEnvelopeversion() {
        return envelopeversion;
    }

    /**
     * 获取对象
     *
     * @return 单例对象
     */
    public static KSoap2RetrofitHelper getInstance() {
        if (kSoap2RetrofitHelper == null) {
            synchronized (KSoap2RetrofitHelper.class) {
                if (kSoap2RetrofitHelper == null) {
                    kSoap2RetrofitHelper = new KSoap2RetrofitHelper();
                }
            }
        }
        return kSoap2RetrofitHelper;
    }


    /**
     * 转换ksoap请求头和请求体
     *
     * @param method     方法名
     * @param nameSpace 命名空间
     * @param properties 键值对
     * @return 转换后的请求头和请求体
     */
    public SoapParameter convertRequest(String method, String nameSpace, HashMap<String, Object> properties) {
        //throws HttpResponseException, IOException, XmlPullParserException
        SoapObject rpc = new SoapObject(nameSpace, method);
        if (properties != null) {
            for (Iterator<Map.Entry<String, Object>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> entry = it.next();
                rpc.addProperty(entry.getKey(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envelopeversion);
        new MarshalBase64().register(envelope);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);

        /***************** driver *******************/
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("User-Agent", SoapUtil.USER_AGENT);

        if (envelope.version != SoapSerializationEnvelope.VER12) {
            headerMap.put("SOAPAction", nameSpace + method);
        }

        if (envelope.version == SoapSerializationEnvelope.VER12) {
            headerMap.put("Content-Type", SoapUtil.CONTENT_TYPE_SOAP_XML_CHARSET_UTF_8);
        } else {
            headerMap.put("Content-Type", SoapUtil.CONTENT_TYPE_XML_CHARSET_UTF_8);
        }

        headerMap.put("Accept-Encoding", "");
        byte[] requestData;
        try {
            requestData = SoapUtil.createRequestData(envelope, "UTF-8");
        } catch (IOException e) {
            requestData = new byte[0];
        }
        headerMap.put("Content-Length", "" + requestData.length);
        Log.d(TAG, "headerMap===" + headerMap.toString() + "\nrequestData===" + new String((byte[]) requestData));
        return new SoapParameter(headerMap, requestData);
    }

}