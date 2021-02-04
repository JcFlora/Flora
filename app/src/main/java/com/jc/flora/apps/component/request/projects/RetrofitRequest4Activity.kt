package com.jc.flora.apps.component.request.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    override fun onDestroy() {
        super.onDestroy()
        mSerialObserver.dispose()
        mConcatObserver.dispose()
        mZipObserver.dispose()
    }

    private fun initViews() {
        title = "使用Retrofit进行嵌套请求"
        setContentView(R.layout.activity_request_24)
        mTvContent = findViewById(R.id.tv) as TextView
    }

    /**
     * 串行，有依赖关系
     */
    fun getDataBySerial(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getBySerial("Android",
                2, 1,"13713777777")
                .subscribe(mSerialObserver)
    }

    /**
     * 串行，无依赖关系
     */
    fun getDataByConcat(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getByConcat("Android",
                2, 1,"13713777777")
                .subscribe(mConcatObserver)
    }

    /**
     * 并行
     */
    fun getDataByZip(v: View){
        mProgressDialogDelegate = ProgressDialogDelegate(this)
        mProgressDialogDelegate?.showLoadingDialog()
        GetArticleListAndMobileInfoApi().getByZip("Android",
                2, 1,"13713777777")
                .subscribe(mZipObserver)
    }

    private val mSerialObserver : BaseApi.ObserverAdapter<String> = object : BaseApi.ObserverAdapter<String>() {
        override fun onNext(result: String) {
            if(isDisposed){
                return
            }
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.text = result
        }
    }

    private val mConcatObserver : BaseApi.ObserverAdapter<Any> = object : BaseApi.ObserverAdapter<Any>() {
        override fun onNext(result: Any) {
            if(isDisposed){
                return
            }
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.append("\n")
            if(result is NetResponse){
                mTvContent?.append(result.results[0].toString())
            }else if(result is String){
                mTvContent?.append(result.toString())
            }
        }
    }

    private val mZipObserver : BaseApi.ObserverAdapter<ArrayList<Any>> = object : BaseApi.ObserverAdapter<ArrayList<Any>>() {
        override fun onNext(result: ArrayList<Any>) {
            if(isDisposed){
                return
            }
            mProgressDialogDelegate?.hideLoadingDialog()
            mTvContent?.text = ""
            mTvContent?.append((result[0] as NetResponse).results[0].toString())
            mTvContent?.append("\n")
            mTvContent?.append(result[1].toString())
        }
    }

}