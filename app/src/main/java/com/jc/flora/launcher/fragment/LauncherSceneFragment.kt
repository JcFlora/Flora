package com.jc.flora.launcher.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jc.flora.R
import com.jc.flora.apps.scene.bluetooth.BluetoothActivity
import com.jc.flora.apps.scene.guider.GuiderActivity
import com.jc.flora.apps.scene.login.LoginActivity
import com.jc.flora.apps.scene.splash.SplashActivity
import com.jc.flora.apps.scene.telephone.TelephoneActivity
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment
import com.jc.flora.launcher.LauncherApp
import com.jc.flora.launcher.LauncherAppsAdapter
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/5/22.
 */
class LauncherSceneFragment : CaptainFragment() {

    private var rvApps : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.layout_launcher_fragment, container, false)
        rvApps = v.findViewById(R.id.rv_apps) as RecyclerView
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addApps()
    }

    private fun addApps() {
        rvApps?.layoutManager = GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)
        rvApps?.adapter = LauncherAppsAdapter(activity as AppCompatActivity?, mApps)
    }

    private val mApps: ArrayList<LauncherApp>
        get() {
            return arrayListOf(
                    LauncherApp("闪屏界面", R.mipmap.ic_splash, SplashActivity::class.java),
                    LauncherApp("引导界面", R.mipmap.ic_guider, GuiderActivity::class.java),
                    LauncherApp("登录界面", R.mipmap.ic_login, LoginActivity::class.java),
                    LauncherApp("账户体系", R.mipmap.ic_account, NotFoundActivity::class.java),
                    LauncherApp("图片选择", R.mipmap.ic_photo, NotFoundActivity::class.java),
                    LauncherApp("浏览大图", R.mipmap.ic_album, NotFoundActivity::class.java),
                    LauncherApp("WIFI开发", R.mipmap.ic_wifi, NotFoundActivity::class.java),
                    LauncherApp("蓝牙开发", R.mipmap.ic_bluetooth, BluetoothActivity::class.java),
                    LauncherApp("电话", R.mipmap.ic_telephone, TelephoneActivity::class.java),
                    LauncherApp("分享", R.mipmap.ic_share, NotFoundActivity::class.java),
                    LauncherApp("城市切换", R.mipmap.ic_city, NotFoundActivity::class.java),
                    LauncherApp("定位", R.mipmap.ic_location, NotFoundActivity::class.java),
                    LauncherApp("地图", R.mipmap.ic_map, NotFoundActivity::class.java),
                    LauncherApp("购物车", R.mipmap.ic_cart, NotFoundActivity::class.java)
            )
        }

    override fun onReload(position: Int) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
