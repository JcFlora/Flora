package com.jc.flora.apps.component.upgrade

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.upgrade.projects.SettingsUpgradeActivity
import com.jc.flora.apps.component.upgrade.projects.SplashUpgradeActivity
import java.util.*

/**
 * Created by shijincheng on 2017/3/2.
 */
class UpgradeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "升级更新"
        setContentView(R.layout.activity_projects)
        addProjects()
    }

    private fun addProjects() {
        val rvProjects: RecyclerView = findViewById(R.id.rv_projects)
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
            project.projectName = "0.1：启动检测升级"
            project.targetActivity = SplashUpgradeActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "0.2：手动检测升级"
            project.targetActivity = SettingsUpgradeActivity::class.java
            return project
        }

}
