package com.jc.flora.apps.component.exit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.exit.projects.AppRestartActivity
import com.jc.flora.apps.component.exit.projects.DialogExitActivity
import com.jc.flora.apps.component.exit.projects.DoubleBackExitActivity
import java.util.*

/**
 * Created by shijincheng on 2017/1/25.
 */
class ExitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "退出功能"
        setContentView(R.layout.activity_projects)
        addProjects()
    }

    private fun addProjects() {
        val rvProjects: androidx.recyclerview.widget.RecyclerView = findViewById(R.id.rv_projects)
        rvProjects.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "弹出对话框确认退出"
            project.targetActivity = DialogExitActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "再按一次返回键退出"
            project.targetActivity = DoubleBackExitActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "重启（一键退出+启动首页）"
            project.targetActivity = AppRestartActivity::class.java
            return project
        }

}