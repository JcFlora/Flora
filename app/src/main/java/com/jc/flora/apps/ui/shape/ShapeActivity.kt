package com.jc.flora.apps.ui.shape

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.shape.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/7/8.
 */
class ShapeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "各种形状"
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
            project.projectName = "圆角背景"
            project.targetActivity = ShapeCornerActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "椭圆/圆形背景"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "渐变背景"
            project.targetActivity = ShapeGradientActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "虚线"
            project.targetActivity = ShapeDashActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "高亮反馈"
            project.targetActivity = ShapeSelectorActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "水波纹反馈"
            project.targetActivity = ShapeRippleActivity::class.java
            return project
        }
//
//    private val project5: Project
//        get() {
//            val project = Project()
//            project.projectName = "SnackBar"
//            project.targetActivity = NotFoundActivity::class.java
//            return project
//        }
//
//    private val project6: Project
//        get() {
//            val project = Project()
//            project.projectName = "BottomSheetDialog"
//            project.targetActivity = NotFoundActivity::class.java
//            return project
//        }
//
//    private val project7: Project
//        get() {
//            val project = Project()
//            project.projectName = "Spinner"
//            project.targetActivity = NotFoundActivity::class.java
//            return project
//        }

}