package com.jc.flora.apps.component.render

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.render.projects.RenderColorActivity
import com.jc.flora.apps.component.render.projects.RenderDimen1Activity
import com.jc.flora.apps.component.render.projects.RenderDimen2Activity
import com.jc.flora.apps.component.render.projects.RenderTextActivity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/6/22.
 */
class RenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "设计规范"
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
            return arrayListOf(project1, project2, project3, project4, project5, project6)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "颜色规范"
            project.targetActivity = RenderColorActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "字体规范"
            project.targetActivity = RenderTextActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "尺寸规范（普通）"
            project.targetActivity = RenderDimen1Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "尺寸规范（卡片）"
            project.targetActivity = RenderDimen2Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "反馈规范"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "状态规范"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }
}