package com.jc.flora.apps.component.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.router.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/5/16.
 */
class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "页面路由"
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
                    project6, project7, project8)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "路由0.1：基础路由跳转"
            project.targetActivity = EasyRouterActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "路由0.2：Fragment的路由跳转"
            project.targetActivity = FragmentRouterActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "路由0.3：拦截Url的路由跳转"
            project.targetActivity = UrlRouter1Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "路由0.4：封装成UrlRouterDelegate"
            project.targetActivity = UrlRouter2Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "路由0.5：升级成注册式Url路由（适应组件化开发）"
            project.targetActivity = UrlRouter3Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "路由0.6：下沉接口实现组件间跳转路由（适应组件化开发）"
            project.targetActivity = ModuleRouterActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "路由0.7：合并多种路由"
            project.targetActivity = MultiRouterActivity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "路由0.8：使用阿里ARouter"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}