package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.jc.flora.R
import com.jc.flora.apps.component.request.NetResponse
import com.jc.flora.apps.component.request.retrofit.BaseApi
import com.jc.flora.apps.component.request.retrofit.GetArticleListAndMobileInfoApi
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate

/**
 * 网络请求最新版：使用Retrofit进行嵌套请求
 * Created by shijincheng on 2018/5/4.
 */
class RetrofitRequest4Activity : AppCompatActivity() {

    private var mTvContent: TextView? = null
    private var mProgressDialogDelegate: ProgressDialogDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        title = "使用Retrofit进行嵌套请求"
        setContentView(R.layout.activity_request_24)
        mTvContent = findViewById(R.id.tv) as TextView
    }

    /**
     * 串行，有依赖关系
     */
    public fun getDataBySerial(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getBySerial("Android",
                2, 1,"13713777777")
                .subscribe(object : BaseApi.ObserverAdapter<String>() {
                    override fun onNext(result: String) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.text = result
                    }
                })
    }

    /**
     * 串行，无依赖关系
     */
    public fun getDataByConcat(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getByConcat("Android",
                2, 1,"13713777777")
                .subscribe(object : BaseApi.ObserverAdapter<Any>() {
                    override fun onNext(result: Any) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.append("\n")
                        if(result is NetResponse){
                            mTvContent?.append(result.results[0].toString())
                        }else if(result is String){
                            mTvContent?.append(result.toString())
                        }
                    }
                })
    }

    /**
     * 并行
     */
    public fun getDataByZip(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getByZip("Android",
                2, 1,"13713777777")
                .subscribe(object : BaseApi.ObserverAdapter<ArrayList<Any>>() {
                    override fun onNext(result: ArrayList<Any>) {
                        mProgressDialogDelegate?.hideLoadingDialog()
                        mTvContent?.text = ""
                        mTvContent?.append((result[0] as NetResponse).results[0].toString())
                        mTvContent?.append("\n")
                        mTvContent?.append(result[1].toString())
                    }
                })
    }

}