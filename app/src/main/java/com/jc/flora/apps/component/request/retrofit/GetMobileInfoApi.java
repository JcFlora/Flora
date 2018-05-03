package com.jc.flora.apps.component.request.retrofit;

import com.jc.flora.apps.component.request.retrofit.soap.ksoap2.KSoap2RetrofitHelper;
import com.jc.flora.apps.component.request.retrofit.soap.ksoap2.SoapParameter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Shijincheng on 2018/5/2.
 */

public class GetMobileInfoApi extends BaseApi {

    private static final String GET_MOBILE_INFO_ACTION = "WebServices/MobileCodeWS.asmx";
    private static final String METHOD_NAME = "getMobileCodeInfo";
    private static final String NAME_SPACE = "http://WebXml.com.cn/";

    private interface GetMobileInfoService {
        @POST(GET_MOBILE_INFO_ACTION)
        Observable<String> getMobileInfo(@HeaderMap Map<String,String> headerMap, @Body String body);
    }

    public Observable<String> getMobileInfo(String mobileCode) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("mobileCode", mobileCode);

        SoapParameter soapParameter = KSoap2RetrofitHelper.getInstance().convertRequest(METHOD_NAME, NAME_SPACE, properties);
        Map<String, String> soapHeaderMap = null;
        String body = null;
        if (soapParameter != null) {
            soapHeaderMap = soapParameter.getHeaderMap();
            body = new String(soapParameter.getRequestData());
        }

        return getSoapRetrofit().create(GetMobileInfoApi.GetMobileInfoService.class).getMobileInfo(soapHeaderMap, body)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}