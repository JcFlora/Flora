package com.jc.flora.apps.scene.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.splash.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/1/17.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "闪屏界面"
        setContentView(R.layout.activity_projects)
        addProjects()
    }

    private fun addProjects() {
        val rvProjects: RecyclerView = findViewById(R.id.rv_projects)
        rvProjects.layoutManager = LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3, project4, project5, project6, project7)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.1，基础实现方法"
            project.targetActivity = Splash1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.2，封装优化+屏蔽back键+退出关闭倒计时任务"
            project.targetActivity = Splash2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.3，预加载+跳转到不同界面"
            project.targetActivity = Splash3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.4，覆盖式方案实现分屏过渡动画"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.5，闪屏广告+倒计时显示+skip"
            project.targetActivity = Splash5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.6，闪屏广告+倒计时显示+预加载+后台可配置"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "闪屏界面0.7，闪屏视频广告+倒计时显示+skip"
            project.targetActivity = Splash7Activity::class.java
            return project
        }

}