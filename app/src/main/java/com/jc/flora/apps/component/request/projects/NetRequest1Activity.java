package com.jc.flora.apps.component.request.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jc.flora.apps.component.request.NetResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 最原始版本的网络请求调用
 * Created by shijincheng on 2017/1/12.
 */
public class NetRequest1Activity extends AppCompatActivity {

    private TextView mTvContent;
    private String mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("直接使用HttpURLConnection");
        mTvContent = new TextView(this);
        setContentView(mTvContent);
        // 开启子线程发起请求，如果在主线程中发起，会报错
        mGetThread.start();
    }

    /** 开启子线程发起请求 */
    private Thread mGetThread = new Thread(){
        public void run() {
            sendRequest();
        }
    };

    /**
     * 发起请求，使用HttpURLConnection，为什么不用HttpClient，请看这http://blog.csdn.net/guolin_blog/article/details/12452307
     * 1、url.openConnection()获取HttpURLConnection对象；
     * 2、匹配connection.getResponseCode()是否为200；
     * 3、connection.getInputStream()获取输入流；
     * 4、inputStreamToString(is)解析输入流中的数据，并进行保存；
     * 5、借助Handler通知主线程，在界面上展示
     */
    private void sendRequest(){
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://gank.io/api/search/query/listview/category/Android/count/2/page/1");
            connection = (HttpURLConnection) url.openConnection();
            if(connection.getResponseCode() == 200){
                InputStream is = connection.getInputStream();
                mResult = inputStreamToString(is);
                Message msg = Message.obtain();
                msg.what = 0;
                mGetHandler.sendMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    /**
     * 解析输入流中的数据
     * @param is
     * @return
     */
    private String inputStreamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            return new String(baos.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 获取数据后，用于回调主线程的Handler */
    @SuppressLint("HandlerLeak")
    private Handler mGetHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 0 && mResult !=null){
                try {
                    JSONObject json =new JSONObject(mResult);
                    NetResponse response = new NetResponse(json);
                    mTvContent.setText(response.results.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetHandler.removeCallbacksAndMessages(null);
    }

}
