package com.jc.flora.apps.component.router.projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class GetDataTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "路由传递数据"
        val tv = TextView(this)
        setContentView(tv)
        val from:String? = intent.getStringExtra("from")
        tv.text = "路由传递数据，传递过来的数据为${from}"
    }
}
