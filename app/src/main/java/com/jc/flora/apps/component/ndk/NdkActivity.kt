package com.jc.flora.apps.component.ndk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.ndk.projects.Ndk1Activity
import com.jc.flora.apps.component.ndk.projects.Ndk2Activity
import java.util.*

/**
 * Created by shijincheng on 2018/4/13.
 */
class NdkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "本地开发"
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
            project.projectName = "最基础的JNI调用"
            project.targetActivity = Ndk1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "以源码的形式编译Lame动态库"
            project.targetActivity = Ndk2Activity::class.java
            return project
        }

}