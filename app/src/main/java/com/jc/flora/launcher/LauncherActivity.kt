package com.jc.flora.launcher

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.jc.flora.R
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate

/**
 * Created by shijincheng on 2017/5/2.
 */
class LauncherActivity : AppCompatActivity() {

    /** 确认退出等待时间  */
    private val EXIT_WAIT_TIME: Long = 2000
    /** 第一次按返回的时间点  */
    private var mExitTime: Long = 0

    private var mVpContainer: ViewPager? = null
    private var mLayoutCaptainIndicators: LinearLayout? = null

    private var mDelegate: LauncherCaptainDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initViews()
        initDelegate()
    }

    private fun initViews() {
        mVpContainer = findViewById(R.id.vp_content) as ViewPager
        mLayoutCaptainIndicators = findViewById(R.id.layout_captain_indicators) as LinearLayout
    }

    private fun initDelegate() {
        mDelegate = LauncherCaptainDelegate()
        mDelegate?.setActivity(this)
        mDelegate?.setVpContainer(mVpContainer)
        mDelegate?.setLayoutCaptainIndicators(mLayoutCaptainIndicators)
        mDelegate?.init()
    }

    override fun onBackPressed() {
        if(mDelegate?.back2Tab0()!!){
            return
        }
        if (System.currentTimeMillis() - mExitTime > EXIT_WAIT_TIME) {
            ToastDelegate.show(this, "再按一次返回键退出")
            mExitTime = System.currentTimeMillis()
        } else {
            ToastDelegate.cancel()
            ToastDelegate.onAppExit()
            finish()
        }
    }

}