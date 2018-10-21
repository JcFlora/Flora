package com.jc.flora.apps.component.request.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.apps.component.request.NetResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 网络请求调用升级版，分离请求和输入流解析过程，提取为工具类HttpUtils
 * Created by shijincheng on 2017/1/12.
 */
public class NetRequest2Activity extends AppCompatActivity {

    private TextView mTvContent;
    private String mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装HttpURLConnection为工具类");
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
     * 发起请求，使用HttpUtils
     * 1、HttpUtils.get(url)，返回数据，并进行保存；
     * 2、借助Handler通知主线程，在界面上展示
     */
    private void sendRequest(){
        mResult = HttpUtils.get("https://gank.io/api/search/query/listview/category/Android/count/2/page/1");
        Message msg = Message.obtain();
        msg.what = 0;
        mGetHandler.sendMessage(msg);
    }

    /** 获取数据后，用于回调主线程的Handler */
    @SuppressLint("HandlerLeak")
    private Handler mGetHandler = new Handler(){
        public void handleMessage(Message msg) {
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