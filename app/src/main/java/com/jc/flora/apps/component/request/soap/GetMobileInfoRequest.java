package com.jc.flora.apps.component.request.soap;

import android.content.ContentValues;

import org.ksoap2.serialization.SoapObject;

public abstract class GetMobileInfoRequest extends AbsAsyncTask<String> {

	private String mCity;

	public void sendRequest(String city){
		mCity = city;
		execute();
	}

	@Override
	protected String getNameSpace() {
		return "http://WebXml.com.cn/";
	}

	@Override
	protected String getUrl() {
		return "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx";
	}

	@Override
	protected String getMethodName() {
		return "getMobileCodeInfo";
	}

	@Override
	protected ContentValues params2ContentValues() {
		ContentValues contentValues = new ContentValues(1);
		contentValues.put("mobileCode", mCity);
		return contentValues;
	}

	@Override
	protected String parseResponse(SoapObject soapObject) {
 		return soapObject.getProperty(0).toString();
	}

}
