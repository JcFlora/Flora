package com.jc.flora.apps.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.list.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/9/3.
 */
class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "各种列表"
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
                    project6, project7, project8, project9)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.1：列表组布局视图"
            project.targetActivity = ListSlip1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.2：封装成SlipsView"
            project.targetActivity = ListSlip2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.3：添加多种模板"
            project.targetActivity = ListSlip3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.4：添加单选模式"
            project.targetActivity = ListSlip4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.5：卡片组布局视图"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.6：封装成ScrapsView"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.7：列数动态控制"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.8：边框显示处理"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "列表组0.9：添加角标显示与控制"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}