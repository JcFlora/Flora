package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.jc.flora.apps.component.request.retrofit.BaseApi
import com.jc.flora.apps.component.request.retrofit.GetMobileInfoApi
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求最新版：使用Retrofit+RxJava封装Soap请求
 * Created by shijincheng on 2018/5/2.
 */
class RetrofitRequest3Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
    }

    private fun initViews() {
        title = "使用Retrofit+RxJava封装Soap请求"
        mTvContent = TextView(this)
        setContentView(mTvContent)
    }

    private fun loadData() {
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        GetMobileInfoApi().getMobileInfo("13713777777")
                .subscribe(object : BaseApi.ObserverAdapter<String>() {
                    override fun onNext(result: String) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.text = result
                    }
                })
        mProgressDialogDelegate?.showLoadingDialog()
    }

}