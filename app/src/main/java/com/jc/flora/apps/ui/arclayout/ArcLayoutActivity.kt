package com.jc.flora.apps.ui.arclayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.arclayout.projects.ArcLayout1Activity
import com.jc.flora.apps.ui.arclayout.projects.ArcLayout6Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.ArrayList

/**
 * compile 'com.ogaclejapan.arclayout:library:1.1.0@aar'
 * https://github.com/ogaclejapan/ArcLayout
 * Created by shijincheng on 2017/2/8.
 */
class ArcLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "弧形布局"
        setContentView(R.layout.activity_projects)
        addProjects()
    }

    private fun addProjects() {
        val rvProjects = findViewById(R.id.rv_projects) as RecyclerView
        rvProjects.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, getProjects())
    }

    private fun getProjects() : ArrayList<Project>{
            return arrayListOf(getProject1(), getProject2(), getProject6())
    }

    private fun getProject1(): Project {
        val project = Project()
        project.projectName = "基础弧形布局"
        project.targetActivity = ArcLayout1Activity::class.java
        return project
    }

    private fun getProject2(): Project {
        val project = Project()
        project.projectName = "Path菜单"
        project.targetActivity = NotFoundActivity::class.java
        return project
    }

    private fun getProject6(): Project {
        val project = Project()
        project.projectName = "弧形Tab+指示器"
        project.targetActivity = ArcLayout6Activity::class.java
        return project
    }

}
