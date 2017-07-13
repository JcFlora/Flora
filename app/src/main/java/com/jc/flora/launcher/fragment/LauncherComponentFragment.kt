package com.jc.flora.launcher.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jc.flora.R
import com.jc.flora.apps.component.exit.ExitActivity
import com.jc.flora.apps.component.hybrid.HybridActivity
import com.jc.flora.apps.component.image.ImageLoadActivity
import com.jc.flora.apps.component.render.RenderActivity
import com.jc.flora.apps.component.request.NetRequestActivity
import com.jc.flora.apps.component.router.RouterActivity
import com.jc.flora.apps.component.statistics.StatisticsActivity
import com.jc.flora.apps.component.upgrade.UpgradeActivity
import com.jc.flora.apps.component.vi.ViActivity
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment
import com.jc.flora.launcher.LauncherApp
import com.jc.flora.launcher.LauncherAppsAdapter
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/5/22.
 */
class LauncherComponentFragment : CaptainFragment() {

    private var rvApps: RecyclerView? = null

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
                    LauncherApp("屏幕适配", R.mipmap.ic_vi, ViActivity::class.java),
                    LauncherApp("设计规范", R.mipmap.ic_render, RenderActivity::class.java),
                    LauncherApp("设备信息", R.mipmap.ic_device, NotFoundActivity::class.java),
                    LauncherApp("权限控制", R.mipmap.ic_security, NotFoundActivity::class.java),

                    LauncherApp("退出功能", R.mipmap.ic_exit, ExitActivity::class.java),
                    LauncherApp("版本升级", R.mipmap.ic_upgrade, UpgradeActivity::class.java),
                    LauncherApp("页面路由", R.mipmap.ic_router, RouterActivity::class.java),
                    LauncherApp("混合开发", R.mipmap.ic_hybrid, HybridActivity::class.java),

                    LauncherApp("网络请求", R.mipmap.ic_net, NetRequestActivity::class.java),
                    LauncherApp("图片加载", R.mipmap.ic_image, ImageLoadActivity::class.java),
                    LauncherApp("附件上传", R.mipmap.ic_upload, NotFoundActivity::class.java),
                    LauncherApp("文件下载", R.mipmap.ic_download, NotFoundActivity::class.java),

                    LauncherApp("本地缓存", R.mipmap.ic_cache, NotFoundActivity::class.java),
                    LauncherApp("数据库", R.mipmap.ic_db, NotFoundActivity::class.java),
                    LauncherApp("时间处理", R.mipmap.ic_time, NotFoundActivity::class.java),
                    LauncherApp("数据统计", R.mipmap.ic_statistics, StatisticsActivity::class.java)
            )
        }

    override fun onReload(position: Int) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
