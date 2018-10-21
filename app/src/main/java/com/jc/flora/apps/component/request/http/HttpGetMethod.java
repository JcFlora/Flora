package com.jc.flora.apps.component.request.http;

import android.content.ContentValues;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Map;

class HttpGetMethod {

    private String mUrl;
    private ContentValues mParams;

    HttpGetMethod(String url, ContentValues params) {
        mUrl = url;
        mParams = params;
    }

    String doGet() {
        String url = mUrl.trim().toLowerCase();
        ILog.D("preUrl = " + url);
        if (TextUtils.isEmpty(url) || !(url.startsWith("http://") || url.startsWith("https://"))) {
            return null;
        }

        HttpURLConnection conn = null;
        if (mParams != null && mParams.size() > 0) {
            String para = "";
            for (Map.Entry<String, Object> param : mParams.valueSet()) {
                String key = param.getKey();
                String val = param.getValue().toString();
                para += "&" + key + "=" + val;
            }
            url += "?" + para.substring(1, para.length());
            ILog.D("url = " + url);
        }

        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            int responseCode = conn.getResponseCode();
            if(conn.getResponseCode() == 200){
                return getStringFromInputStream(conn.getInputStream());
            } else {
                ILog.D("response status is " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 解析输入流中的数据
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        // 把流中的数据转换成字符串,采用的编码是utf-8
        String state = os.toString();
        os.close();
        return state;
    }

}