package com.jc.flora.launcher.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jc.flora.R
import com.jc.flora.apps.scene.album.AlbumActivity
import com.jc.flora.apps.scene.bluetooth.BluetoothActivity
import com.jc.flora.apps.scene.guider.GuiderActivity
import com.jc.flora.apps.scene.identity.IdentityActivity
import com.jc.flora.apps.scene.login.LoginActivity
import com.jc.flora.apps.scene.payment.PaymentActivity
import com.jc.flora.apps.scene.preview.PreviewActivity
import com.jc.flora.apps.scene.qrcode.QrcodeActivity
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

    private var rvApps: RecyclerView? = null

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
                    LauncherApp("闪屏界面", R.mipmap.ic_splash, SplashActivity::class.java),
                    LauncherApp("引导界面", R.mipmap.ic_guider, GuiderActivity::class.java),
                    LauncherApp("登录界面", R.mipmap.ic_login, LoginActivity::class.java),
                    LauncherApp("身份验证", R.mipmap.ic_identity, IdentityActivity::class.java),

                    LauncherApp("图片选择", R.mipmap.ic_album, AlbumActivity::class.java),
                    LauncherApp("大图预览", R.mipmap.ic_preview, PreviewActivity::class.java),
                    LauncherApp("内容分享", R.mipmap.ic_share, NotFoundActivity::class.java),
                    LauncherApp("二维码", R.mipmap.ic_qrcode, QrcodeActivity::class.java),

                    LauncherApp("电商首页", R.mipmap.ic_home, NotFoundActivity::class.java),
                    LauncherApp("商品分类", R.mipmap.ic_category, NotFoundActivity::class.java),
                    LauncherApp("购物车", R.mipmap.ic_cart, NotFoundActivity::class.java),
                    LauncherApp("个人中心", R.mipmap.ic_my, NotFoundActivity::class.java),

                    LauncherApp("热门推荐", R.mipmap.ic_hot, NotFoundActivity::class.java),
                    LauncherApp("搜索商品", R.mipmap.ic_search, NotFoundActivity::class.java),
                    LauncherApp("商品详情", R.mipmap.ic_product, NotFoundActivity::class.java),
                    LauncherApp("订单管理", R.mipmap.ic_order, NotFoundActivity::class.java),

                    LauncherApp("在线支付", R.mipmap.ic_payment, PaymentActivity::class.java),
                    LauncherApp("城市切换", R.mipmap.ic_city, NotFoundActivity::class.java),
                    LauncherApp("地理定位", R.mipmap.ic_location, NotFoundActivity::class.java),
                    LauncherApp("地图开发", R.mipmap.ic_map, NotFoundActivity::class.java),

                    LauncherApp("消息推送", R.mipmap.ic_push, NotFoundActivity::class.java),
                    LauncherApp("即时通讯", R.mipmap.ic_chat, NotFoundActivity::class.java),
                    LauncherApp("语音识别", R.mipmap.ic_voice, NotFoundActivity::class.java),
                    LauncherApp("拨打电话", R.mipmap.ic_telephone, TelephoneActivity::class.java),

                    LauncherApp("WIFI开发", R.mipmap.ic_wifi, NotFoundActivity::class.java),
                    LauncherApp("蓝牙开发", R.mipmap.ic_bluetooth, BluetoothActivity::class.java),
                    LauncherApp("直播技术", R.mipmap.ic_live, NotFoundActivity::class.java),
                    LauncherApp("有声电台", R.mipmap.ic_fm, NotFoundActivity::class.java),

                    LauncherApp("账户体系", R.mipmap.ic_account, NotFoundActivity::class.java),
                    LauncherApp("会员互动", R.mipmap.ic_member, NotFoundActivity::class.java),
                    LauncherApp("设置界面", R.mipmap.ic_settings, NotFoundActivity::class.java),
                    LauncherApp("摇一摇", R.mipmap.ic_shake, NotFoundActivity::class.java)
            )
        }

    override fun onReload(position: Int) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
