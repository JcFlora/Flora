package com.jc.flora.launcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.jc.flora.R

class NotFoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val iv = ImageView(this)
        setContentView(iv)
        iv.scaleType = ImageView.ScaleType.FIT_CENTER
        iv.setImageResource(R.drawable.bg_not_found)
    }
}
