package com.jc.flora.apps.component.request

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.request.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.ArrayList

class NetRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "网络请求"
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
            return arrayListOf(project1, project2, project4, project5, project6, project8)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.1，直接使用HttpURLConnection实现"
            project.targetActivity = NetRequest1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.2，将HttpURLConnection请求网络封装成工具类"
            project.targetActivity = NetRequest2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.3，将HttpURLConnection请求网络封装成框架"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.4，使用Volley请求，String接收"
            project.targetActivity = NetRequest4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.5，使用Volley+Gson"
            project.targetActivity = NetRequest5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.6，使用Volley+Gson+通用返回实体"
            project.targetActivity = NetRequest6Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.8，使用Retrofit+RxJava"
            project.targetActivity = NetRequest8Activity::class.java
            return project
        }

}
