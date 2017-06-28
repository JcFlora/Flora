package com.jc.flora.apps.component.request.projects;

import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求工具类，封装HttpURLConnection的请求过程
 * Created by shijincheng on 2017/1/13.
 */
public class HttpUtils {

    /**
     * 发起post请求
     * @param url
     * @param content
     * @return
     */
    public static String post(String url, String content) {
        HttpURLConnection conn = null;
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
            // post请求的参数
            String data = content;
            // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            // 调用此方法就不必再使用conn.connect()方法
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
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
     * 发起get请求
     * @param url
     * @return
     */
    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            int responseCode = conn.getResponseCode();
            if(conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
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
