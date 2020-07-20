package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.component.request.http.GetArticleListRequest
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求调用升级版，分离请求和输入流解析过程，通过AsyncTask封装为成框架
 * Created by shijincheng on 2018/4/16.
 */
class NetRequest3Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用AsyncTask封装Http请求框架"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        MyRequest().sendRequest("Android", 2, 1)
    }

    private inner class MyRequest : GetArticleListRequest() {
        override fun updateUIAfterGetData(result: NetResponse?) {
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.text = result!!.results[0].toString()
        }
    }

}