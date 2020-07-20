package com.jc.flora.apps.ui.bezier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.bezier.projects.Bezier1Activity
import com.jc.flora.apps.ui.bezier.projects.Bezier2Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/9/7.
 */
class BezierActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "贝塞尔曲线"
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
            return arrayListOf(project1, project2, project3, project4, project5, project6, project7)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "2阶与3阶贝塞尔曲线"
            project.targetActivity = Bezier1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "拐点跟随手指移动的2阶贝塞尔曲线"
            project.targetActivity = Bezier2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}