package com.jc.flora.apps.scene.telephone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.telephone.projects.Telephone1Activity
import com.jc.flora.apps.scene.telephone.projects.Telephone2Activity
import java.util.*

/**
 * Created by shijincheng on 2017/7/2.
 */
class TelephoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "电话"
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
            return arrayListOf(project1, project2)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "拨打电话"
            project.targetActivity = Telephone1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "拨打电话(基于RxJava)"
            project.targetActivity = Telephone2Activity::class.java
            return project
        }

}