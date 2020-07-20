package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.component.request.retrofit.BaseApi
import com.jc.flora.apps.component.request.retrofit.GetArticleListApi
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求最新版：使用Retrofit+RxJava+Gson
 * Created by shijincheng on 2017/1/12.
 */
class RetrofitRequest2Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用Retrofit+RxJava+Gson"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetArticleListApi().getArticleList2("Android", 2, 1)
                .subscribe(object : BaseApi.ObserverAdapter<NetResponse>() {
                    override fun onNext(netResponse: NetResponse) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.text = netResponse.results[0].toString()
                    }
                })
        mProgressDialogDelegate?.showLoadingDialog()
    }

}
