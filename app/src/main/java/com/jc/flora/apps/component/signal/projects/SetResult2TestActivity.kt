package com.jc.flora.apps.component.signal.projects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import com.jc.flora.apps.component.signal.delegate.Signal2Delegate

class SetResult2TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "通过setResult回传数据"
        val btn = Button(this)
        setContentView(btn, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        btn.text = "点击返回并回传数据"
        btn.setOnClickListener { _ ->
            Signal2Delegate.setResult(this,"SetResult2TestActivity")
        }
    }
}