package com.jc.flora.apps.component.router

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.router.projects.EasyRouterActivity
import com.jc.flora.apps.component.router.projects.FragmentRouterActivity
import com.jc.flora.apps.component.router.projects.UrlRouterActivity
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
        rvProjects.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "页面路由0.1：基础路由跳转"
            project.targetActivity = EasyRouterActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "页面路由0.2：Fragment的路由跳转"
            project.targetActivity = FragmentRouterActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "页面路由0.3：拦截Url的路由跳转"
            project.targetActivity = UrlRouterActivity::class.java
            return project
        }

}