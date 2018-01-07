package com.jc.flora.apps.component.router.projects

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button

class ReceiveDataTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "路由回传数据"
        val btn = Button(this)
        setContentView(btn, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
        btn.text = "点击返回并回传数据"
        btn.setOnClickListener { _ ->
            val intent = Intent()
            intent.putExtra("result","ReceiveDataTestActivity")
            setResult(1, intent)
            finish()
        }
    }
}
