package com.jc.flora.apps.component.request.http;

import android.content.ContentValues;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Map;

class HttpPostMethod {

    private String mUrl;
    private ContentValues mParams;

    HttpPostMethod(String url, ContentValues params) {
        mUrl = url;
        mParams = params;
    }

    String doPost() {
        String url = mUrl.trim().toLowerCase();
        ILog.D("preUrl = " + url);
        if (TextUtils.isEmpty(url) || !(url.startsWith("http://") || url.startsWith("https://"))) {
            return null;
        }

        HttpURLConnection conn = null;
        // post请求的参数
        String data = "";
        if (mParams != null && mParams.size() > 0) {
            String para = "";
            for (Map.Entry<String, Object> param : mParams.valueSet()) {
                String key = param.getKey();
                String val = param.getValue().toString();
                para += "&" + key + "=" + val;
            }
            data = para.substring(1, para.length());
        }

        try {
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置请求方法为post
            conn.setRequestMethod("POST");
            // 设置读取超时为5秒
            conn.setReadTimeout(5000);
            // 设置连接网络超时为10秒
            conn.setConnectTimeout(10000);
            // 设置此方法,允许向服务器输出内容
            conn.setDoOutput(true);
            // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            // 调用此方法就不必再使用conn.connect()方法
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return getStringFromInputStream(conn.getInputStream());
            } else {
                ILog.D("response status is " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                // 关闭连接
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