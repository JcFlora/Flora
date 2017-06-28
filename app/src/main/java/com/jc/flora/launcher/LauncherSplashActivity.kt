package com.jc.flora.launcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.jc.flora.apps.scene.splash.delegate.Splash2Delegate
import com.jc.flora.apps.ui.stable.delegate.StableDelegate

class LauncherSplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StableDelegate(this).hideStatusBar()
        Splash2Delegate().setTargetActivity(LauncherActivity::class.java).addToActivity(this, "splashDelegate")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return true
    }

}