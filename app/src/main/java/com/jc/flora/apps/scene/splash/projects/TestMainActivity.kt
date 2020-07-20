package com.jc.flora.apps.scene.splash.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

import org.w3c.dom.Text

/**
 * Created by shijincheng on 2017/1/17.
 */
class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "测试主页面"
        val tv = TextView(this)
        setContentView(tv)
        tv.text = "从闪屏页面跳转过来的测试主页面"
    }

}
