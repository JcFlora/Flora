package com.jc.flora.apps.ui.reload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.reload.projects.Reload1Activity
import com.jc.flora.apps.ui.reload.projects.Reload2Activity
import com.jc.flora.apps.ui.reload.projects.Reload3Activity
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class ReloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "下拉刷新"
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
            project.projectName = "使用SwipeRefreshLayout"
            project.targetActivity = Reload1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "实现点击刷新和自动刷新"
            project.targetActivity = Reload2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "实现点击back刷新"
            project.targetActivity = Reload3Activity::class.java
            return project
        }

}