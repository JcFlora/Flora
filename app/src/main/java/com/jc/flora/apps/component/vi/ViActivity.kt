package com.jc.flora.apps.component.vi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.vi.projects.*
import java.util.*

/**
 * Created by shijincheng on 2017/6/7.
 */
class ViActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "屏幕适配"
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
                    project6, project7, project8)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "使用Weight适配控件"
            project.targetActivity = ViWeight1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "使用Weight适配间距"
            project.targetActivity = ViWeight2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "使用Weight适配间距2"
            project.targetActivity = ViWeight3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "使用百分比布局适配"
            project.targetActivity = ViPercentActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "使用约束布局适配控件"
            project.targetActivity = ViConstraint1Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "使用约束布局适配间距"
            project.targetActivity = ViConstraint2Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "使用代码计算适配"
            project.targetActivity = ViCodeActivity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "使用Fidelity组件适配"
            project.targetActivity = ViFidelityActivity::class.java
            return project
        }

}