package com.jc.flora.apps.component.cache

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.cache.projects.CacherActivity
import com.jc.flora.apps.component.cache.projects.SpreaderActivity
import java.util.*

/**
 * Created by shijincheng on 2017/1/25.
 */
class CacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "本地缓存"
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
            project.projectName = "静态变量缓存"
            project.targetActivity = CacherActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "sp缓存"
            project.targetActivity = SpreaderActivity::class.java
            return project
        }

}