package com.jc.flora.launcher

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import com.jc.flora.R
import com.jc.flora.apps.component.exit.delegate.DoubleClickDelegate
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate

/**
 * Created by shijincheng on 2017/5/2.
 */
class LauncherActivity : AppCompatActivity() {

    private var mVpContainer: ViewPager? = null
    private var mLayoutCaptainIndicators: LinearLayout? = null

    private var mDelegate: LauncherCaptainDelegate? = null
    private var mDoubleClickDelegate: DoubleClickDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initViews()
        initDelegate()
    }

    private fun initViews() {
        mVpContainer = findViewById(R.id.vp_content)
        mLayoutCaptainIndicators = findViewById(R.id.layout_captain_indicators)
    }

    private fun initDelegate() {
        mDelegate = LauncherCaptainDelegate()
        mDelegate?.setActivity(this)
        mDelegate?.setVpContainer(mVpContainer)
        mDelegate?.setLayoutCaptainIndicators(mLayoutCaptainIndicators)
        mDelegate?.init()
        mDoubleClickDelegate = DoubleClickDelegate()
    }

    override fun onBackPressed() {
        if(mDelegate?.back2Tab0()!!){
            return
        }
        if(mDoubleClickDelegate?.isDoubleClick!!){
            ToastDelegate.cancel()
            ToastDelegate.onAppExit()
            finish()
        }else{
            ToastDelegate.show(this, "再按一次返回键退出")
        }
    }

}