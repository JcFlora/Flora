package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.component.request.nao.GetArticleListApi
import com.jc.flora.apps.component.request.nao.GetMobileInfoApi
import com.jc.flora.apps.component.request.nao.RequestCallback
import com.jc.flora.apps.component.request.nao.RequestManager
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求调用升级版，使用原生ThreadPoolExecutor+Runnable+Handler
 * Created by shijincheng on 2018/5/6.
 */
class NetRequest6Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用线程池封装Soap请求框架"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetArticleListApi(object : RequestCallback<NetResponse> {
            override fun onSuccess(content: NetResponse?) {
                mProgressDialogDelegate?.hideLoadingDialog()
                mTvContent?.append("\n")
                mTvContent?.append(content!!.results[0].toString())
            }

            override fun onFail(errorMessage: String) {
            }
        }).sendRequest("Android", 2, 1)
        GetMobileInfoApi(object : RequestCallback<String> {
            override fun onSuccess(content: String?) {
                mProgressDialogDelegate?.hideLoadingDialog()
                mTvContent?.append("\n")
                mTvContent?.append(content.toString())
            }

            override fun onFail(errorMessage: String?) {
            }
        }).sendRequest("137137777777")
    }

    override fun onDestroy() {
        super.onDestroy()
        RequestManager.getInstance().cancelAllRequests()
    }

}