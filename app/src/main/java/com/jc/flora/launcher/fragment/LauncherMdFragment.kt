package com.jc.flora.launcher.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jc.flora.R
import com.jc.flora.apps.md.toolbar.ToolbarActivity
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment
import com.jc.flora.launcher.LauncherApp
import com.jc.flora.launcher.LauncherCardsAdapter
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/6/12.
 */
class LauncherMdFragment : CaptainFragment() {

    private var rvApps : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.layout_launcher_fragment, container, false)
        rvApps = v.findViewById(R.id.rv_apps) as RecyclerView
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addApps()
    }

    private fun addApps() {
        rvApps?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvApps?.adapter = LauncherCardsAdapter(activity as AppCompatActivity?, mApps)
    }

    private val mApps: ArrayList<LauncherApp>
        get() {
            return arrayListOf(
                    LauncherApp("Toolbar", R.mipmap.ic_bow, ToolbarActivity::class.java),
                    LauncherApp("FloatActionButton", R.mipmap.ic_bow, NotFoundActivity::class.java),
                    LauncherApp("ConstraintLayout", R.mipmap.ic_chain, NotFoundActivity::class.java)
            )
        }

    override fun onReload(position: Int) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
