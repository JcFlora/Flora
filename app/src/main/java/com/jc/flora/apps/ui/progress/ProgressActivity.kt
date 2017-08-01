package com.jc.flora.apps.ui.progress

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.progress.projects.Progress1Activity
import java.util.*

/**
 * Created by shijincheng on 2017/7/27.
 */
class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "进度条"
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
            return arrayListOf(project1)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "使用ClipDrawable实现"
            project.targetActivity = Progress1Activity::class.java
            return project
        }

//    private val project2: Project
//        get() {
//            val project = Project()
//            project.projectName = "椭圆/圆形背景"
//            project.targetActivity = ShapeOvalActivity::class.java
//            return project
//        }
//
//    private val project3: Project
//        get() {
//            val project = Project()
//            project.projectName = "渐变背景"
//            project.targetActivity = ShapeGradientActivity::class.java
//            return project
//        }
//
//    private val project4: Project
//        get() {
//            val project = Project()
//            project.projectName = "虚线"
//            project.targetActivity = ShapeDashActivity::class.java
//            return project
//        }
//
//    private val project5: Project
//        get() {
//            val project = Project()
//            project.projectName = "高亮反馈"
//            project.targetActivity = ShapeSelectorActivity::class.java
//            return project
//        }
//
//    private val project6: Project
//        get() {
//            val project = Project()
//            project.projectName = "水波纹反馈"
//            project.targetActivity = ShapeRippleActivity::class.java
//            return project
//        }

}