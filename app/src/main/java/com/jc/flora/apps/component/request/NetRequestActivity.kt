package com.jc.flora.apps.component.request

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.request.projects.*
import java.util.*

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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project11, project12, project13, project21, project22,
                    project23, project24)
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
            project.projectName = "网络请求0.3，使用AsyncTask封装Http请求框架"
            project.targetActivity = NetRequest3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.4，使用线程池封装Http请求框架"
            project.targetActivity = NetRequest4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "网络请求0.5，使用AsyncTask封装Soap请求框架"
            project.targetActivity = NetRequest5Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "Volley请求0.1，使用Volley请求，String接收"
            project.targetActivity = VolleyRequest1Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "Volley请求0.2，使用Volley+Gson"
            project.targetActivity = VolleyRequest2Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "Volley请求0.3，使用Volley+Gson+通用返回实体"
            project.targetActivity = VolleyRequest3Activity::class.java
            return project
        }

    private val project21: Project
        get() {
            val project = Project()
            project.projectName = "Retrofit请求0.1，使用Retrofit+RxJava，String接收"
            project.targetActivity = RetrofitRequest1Activity::class.java
            return project
        }

    private val project22: Project
        get() {
            val project = Project()
            project.projectName = "Retrofit请求0.2，使用Retrofit+RxJava+Gson"
            project.targetActivity = RetrofitRequest2Activity::class.java
            return project
        }

    private val project23: Project
        get() {
            val project = Project()
            project.projectName = "Retrofit请求0.3，使用Retrofit+RxJava封装Soap请求"
            project.targetActivity = RetrofitRequest3Activity::class.java
            return project
        }

    private val project24: Project
        get() {
            val project = Project()
            project.projectName = "Retrofit请求0.4，使用Retrofit进行嵌套请求"
            project.targetActivity = RetrofitRequest4Activity::class.java
            return project
        }

}