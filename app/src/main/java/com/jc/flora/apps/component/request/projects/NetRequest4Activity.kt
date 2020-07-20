package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.component.request.trh.GetArticleListApi
import com.jc.flora.apps.component.request.trh.RequestCallback
import com.jc.flora.apps.component.request.trh.RequestManager
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求调用升级版，使用原生ThreadPoolExecutor+Runnable+Handler
 * Created by shijincheng on 2018/4/18.
 */
class NetRequest4Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用线程池封装Http请求框架"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetArticleListApi(object : RequestCallback<NetResponse> {
            override fun onSuccess(content: NetResponse?) {
                mProgressDialogDelegate?.hideLoadingDialog()
                mTvContent?.text = content!!.results[0].toString()
            }

            override fun onFail(errorMessage: String) {
            }
        }).sendRequest("Android", 2, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        RequestManager.getInstance().cancelAllRequests()
    }

}