package com.jc.flora.apps.ui.banner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.banner.projects.*
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class BannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "轮播广告"
        setContentView(R.layout.activity_projects)
        addProjects()
    }

    private fun addProjects() {
        val rvProjects = findViewById(R.id.rv_projects) as RecyclerView
        rvProjects.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16, project17, project18, project19, project20,
                    project21, project22)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.1：手动翻页"
            project.targetActivity = Banner1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.2：无限循环"
            project.targetActivity = Banner2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.3：自动翻页"
            project.targetActivity = Banner3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.4：自动翻页优化（手动停止+修改切换速度）"
            project.targetActivity = Banner4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.5：静态添加指示器"
            project.targetActivity = Banner5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.6：动态添加指示器item"
            project.targetActivity = Banner6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.7：加载网络图片"
            project.targetActivity = Banner7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.8：动态添加指示器item封装"
            project.targetActivity = Banner8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "轮播0.9：指示器可配置化"
            project.targetActivity = Banner9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.0：动态添加指示器container封装"
            project.targetActivity = Banner10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.1：添加点击事件"
            project.targetActivity = Banner11Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.2：支持2张以下图片"
            project.targetActivity = Banner12Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.3：支持1张图片不可滑"
            project.targetActivity = Banner13Activity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.4：支持浮动式指示器"
            project.targetActivity = Banner14Activity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.5：处理浮动式指示器边界问题"
            project.targetActivity = Banner15Activity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.6：合并两种指示器"
            project.targetActivity = Banner16Activity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.7：封装成BannerDelegate"
            project.targetActivity = Banner17Activity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.8：添加炫酷的切换动画"
            project.targetActivity = Banner18Activity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = "轮播1.9：封装成BannerView"
            project.targetActivity = Banner19Activity::class.java
            return project
        }

    private val project20: Project
        get() {
            val project = Project()
            project.projectName = "轮播2.0：BannerView优化，改变Delegate方式"
            project.targetActivity = Banner20Activity::class.java
            return project
        }

    private val project21: Project
        get() {
            val project = Project()
            project.projectName = "轮播2.1：BannerView优化，添加数据投放器，支持刷新"
            project.targetActivity = Banner21Activity::class.java
            return project
        }

    private val project22: Project
        get() {
            val project = Project()
            project.projectName = "轮播2.2：电商平台上的应用"
            project.targetActivity = Banner22Activity::class.java
            return project
        }

}