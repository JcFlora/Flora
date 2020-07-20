package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.retrofit.BaseApi
import com.jc.flora.apps.component.request.retrofit.GetArticleListApi
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求最新版：使用Retrofit+RxJava，String接收
 * Created by shijincheng on 2017/1/12.
 */
class RetrofitRequest1Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用Retrofit+RxJava，String接收"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetArticleListApi().getArticleList("Android", 2, 1)
                .subscribe(object : BaseApi.ObserverAdapter<String>() {
                    override fun onNext(netResponse: String) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.text = netResponse
                    }
                })
        mProgressDialogDelegate?.showLoadingDialog()
    }

}
