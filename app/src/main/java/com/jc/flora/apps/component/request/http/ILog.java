package com.jc.flora.apps.component.request.http;

import android.util.Log;

public class ILog {

	private static final String TAG = ">>>>LogInfo<<<<";

	public static void D(String info)
	{
		Log.e(TAG,info);
	}
}
