package com.jc.flora.apps.ui.captain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.captain.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class CaptainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "页面导航"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16, project17, project18, project19, project20,
                    project21)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.1：基础导航布局"
            project.targetActivity = Captain1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.2：导航按钮状态切换"
            project.targetActivity = Captain2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.3：封装导航条"
            project.targetActivity = Captain3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.4：动态添加指示器item"
            project.targetActivity = Captain4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.5：关联页面内容切换"
            project.targetActivity = Captain5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.6：封装Fragment切换"
            project.targetActivity = Captain6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.7：指示器可配置化"
            project.targetActivity = Captain7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.8：舵式导航"
            project.targetActivity = Captain8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "页面导航0.9：舵式导航放大效果"
            project.targetActivity = Captain9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.0：滑动切换内容页面"
            project.targetActivity = Captain10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.1：高仿微信指示器动画"
            project.targetActivity = Captain11Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.2：代码优化+页面缓存优化"
            project.targetActivity = Captain12Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.3：添加角标显示与控制"
            project.targetActivity = Captain13Activity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.4：页面登录拦截"
            project.targetActivity = Captain14Activity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.5：使用索引记录进行重构"
            project.targetActivity = Captain15Activity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.6：再按一次刷新功能"
            project.targetActivity = Captain16Activity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.7：浮动导航条（沉浸列表）"
            project.targetActivity = Captain17Activity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.8：back返回第一个Tab"
            project.targetActivity = Captain18Activity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = "页面导航1.9：隔离出CaptainDelegate"
            project.targetActivity = Captain19Activity::class.java
            return project
        }

    private val project20: Project
        get() {
            val project = Project()
            project.projectName = "页面导航2.0：使用官方TabLayout实现"
            project.targetActivity = Captain20Activity::class.java
            return project
        }

    private val project21: Project
        get() {
            val project = Project()
            project.projectName = "页面导航2.1：使用官方BottomNavigationView实现"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }
}