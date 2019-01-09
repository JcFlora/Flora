package com.jc.flora.apps.md.toolbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.md.toolbar.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/6/18.
 */
class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Toolbar"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10, project11)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "图标和标题的定制"
            project.targetActivity = Toolbar1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "菜单定制"
            project.targetActivity = Toolbar2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "自定义居中标题"
            project.targetActivity = Toolbar3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "溢出菜单添加图标：通过xml"
            project.targetActivity = Toolbar4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "溢出菜单添加图标：通过反射"
            project.targetActivity = Toolbar5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "溢出菜单添加图标：通过自定义弹出窗"
            project.targetActivity = Toolbar6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "修改溢出菜单位置+仿钉钉Toolbar"
            project.targetActivity = Toolbar7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "仿知乎Toolbar"
            project.targetActivity = Toolbar8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "仿QQToolbar"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "实现搜索栏"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "实现搜索栏动态切换"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
