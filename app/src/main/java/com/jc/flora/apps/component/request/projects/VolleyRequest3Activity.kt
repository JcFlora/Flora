package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.android.volley.Response
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate
import com.jc.flora.apps.component.request.volley.GetArticleListApi3

/**
 * 网络请求经典版：使用Volley+Gson+通用返回实体
 * compile 'com.android.volley:volley:1.0.0'
 * compile 'com.google.code.gson:gson:2.8.0'
 * Created by shijincheng on 2017/3/18.
 */
class VolleyRequest3Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用Volley+Gson+通用返回实体"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetArticleListApi3(this, Response.Listener<NetResponse> { response ->
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.text = response.results[0].toString()
        }).sendRequest("Android", 2, 1)
        mProgressDialogDelegate?.showLoadingDialog()
    }

}