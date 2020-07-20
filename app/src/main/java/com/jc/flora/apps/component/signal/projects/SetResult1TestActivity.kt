package com.jc.flora.apps.component.signal.projects

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button

class SetResult1TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "通过setResult回传数据"
        val btn = Button(this)
        setContentView(btn, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        btn.text = "点击返回并回传数据"
        btn.setOnClickListener { _ ->
            val intent = Intent()
            intent.putExtra("result","SetResult1TestActivity")
            setResult(1, intent)
            finish()
        }
    }
}