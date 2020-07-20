package com.jc.flora.launcher.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jc.flora.R
import com.jc.flora.apps.ui.arclayout.ArcLayoutActivity
import com.jc.flora.apps.ui.badge.BadgeActivity
import com.jc.flora.apps.ui.banner.BannerActivity
import com.jc.flora.apps.ui.bezier.BezierActivity
import com.jc.flora.apps.ui.bgchange.BgChangeActivity
import com.jc.flora.apps.ui.blur.BlurActivity
import com.jc.flora.apps.ui.buoy.BuoyActivity
import com.jc.flora.apps.ui.captain.CaptainActivity
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment
import com.jc.flora.apps.ui.dialog.DialogActivity
import com.jc.flora.apps.ui.list.ListActivity
import com.jc.flora.apps.ui.literacy.LiteracyActivity
import com.jc.flora.apps.ui.marquee.MarqueeActivity
import com.jc.flora.apps.ui.nest.NestActivity
import com.jc.flora.apps.ui.progress.ProgressActivity
import com.jc.flora.apps.ui.reload.ReloadActivity
import com.jc.flora.apps.ui.shape.ShapeActivity
import com.jc.flora.apps.ui.stable.StableActivity
import com.jc.flora.apps.ui.steps.StepsActivity
import com.jc.flora.apps.ui.transition.TransitionActivity
import com.jc.flora.apps.ui.typeface.TypefaceActivity
import com.jc.flora.launcher.LauncherApp
import com.jc.flora.launcher.LauncherAppsAdapter
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/5/22.
 */
class LauncherUiFragment : CaptainFragment() {

    private var rvApps : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.layout_launcher_fragment, container, false)
        rvApps = v.findViewById(R.id.rv_apps)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addApps()
    }

    private fun addApps() {
        rvApps?.layoutManager = GridLayoutManager(activity, 4, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false)
        rvApps?.adapter = LauncherAppsAdapter(activity as AppCompatActivity?, mApps)
    }

    private val mApps: ArrayList<LauncherApp>
        get() {
            return arrayListOf(
                    LauncherApp("沉浸模式", R.mipmap.ic_stable, StableActivity::class.java),
                    LauncherApp("各种弹窗", R.mipmap.ic_dialog, DialogActivity::class.java),
                    LauncherApp("各种形状", R.mipmap.ic_shape, ShapeActivity::class.java),
                    LauncherApp("各种列表", R.mipmap.ic_list, ListActivity::class.java),

                    LauncherApp("页面导航", R.mipmap.ic_captain, CaptainActivity::class.java),
                    LauncherApp("内容导航", R.mipmap.ic_sail, NotFoundActivity::class.java),
                    LauncherApp("抽屉导航", R.mipmap.ic_drawer, NotFoundActivity::class.java),
                    LauncherApp("轮播广告", R.mipmap.ic_banner, BannerActivity::class.java),

                    LauncherApp("下拉刷新", R.mipmap.ic_reload, ReloadActivity::class.java),
                    LauncherApp("消息角标", R.mipmap.ic_badge, BadgeActivity::class.java),
                    LauncherApp("悬浮窗口", R.mipmap.ic_buoy, BuoyActivity::class.java),
                    LauncherApp("跑马灯", R.mipmap.ic_marquee, MarqueeActivity::class.java),

                    LauncherApp("转场动画", R.mipmap.ic_transition, TransitionActivity::class.java),
                    LauncherApp("背景切换", R.mipmap.ic_bg, BgChangeActivity::class.java),
                    LauncherApp("模糊效果", R.mipmap.ic_blur, BlurActivity::class.java),
                    LauncherApp("图像处理", R.mipmap.ic_ps, BlurActivity::class.java),

                    LauncherApp("嵌套显示", R.mipmap.ic_nest, NestActivity::class.java),
                    LauncherApp("分步完成", R.mipmap.ic_steps, StepsActivity::class.java),
                    LauncherApp("进度条", R.mipmap.ic_progress, ProgressActivity::class.java),
                    LauncherApp("贝塞尔曲线", R.mipmap.ic_bezier, BezierActivity::class.java),

                    LauncherApp("弧形布局", R.mipmap.ic_arc, ArcLayoutActivity::class.java),
                    LauncherApp("特殊字库", R.mipmap.ic_typeface, TypefaceActivity::class.java),
                    LauncherApp("拼音识字", R.mipmap.ic_literacy, LiteracyActivity::class.java)
            )
        }

    override fun onReload(position: Int) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}