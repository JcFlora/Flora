package com.jc.flora.apps.scene.splash.projects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

/**
 * Created by shijincheng on 2017/1/17.
 */
class TestLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "测试登录页"
        val tv = TextView(this)
        setContentView(tv)
        tv.text = "从闪屏页面跳转过来的登录主页面"
    }

}
