package com.jc.flora.apps.ui.marquee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.marquee.projects.Marquee2Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/6/7.
 */
class MarqueeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "跑马灯"
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
            return arrayListOf(project1, project2, project3, project4)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "横向跑马灯"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "垂直跑马灯"
            project.targetActivity = Marquee2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "垂直跑马灯"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "京东头条"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
