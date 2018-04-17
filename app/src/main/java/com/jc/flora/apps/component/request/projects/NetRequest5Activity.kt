package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.soap.GetMobileInfoRequest
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * Soap网络请求调用，分离请求和输入流解析过程，通过AsyncTask封装为成框架
 * Created by shijincheng on 2018/4/16.
 */
class NetRequest5Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用AsyncTask封装Soap请求框架"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        MyRequest().sendRequest("13713777777")
    }

    private inner class MyRequest : GetMobileInfoRequest() {
        override fun updateUIAfterGetData(result: String?) {
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.text = result
        }

    }

}