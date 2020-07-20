package com.jc.flora.apps.component.permit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.permit.projects.Permit1Activity
import com.jc.flora.apps.component.permit.projects.Permit2Activity
import com.jc.flora.apps.component.permit.projects.Permit3Activity
import com.jc.flora.apps.component.permit.projects.Permit4Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by Shijincheng on 2020/5/19.
 */
class PermitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "权限控制"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "页面触发申请单个固定权限"
            project.targetActivity = Permit1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "页面触发申请单个固定权限+申请结果回调"
            project.targetActivity = Permit2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "拒绝后展示对话框进行提醒"
            project.targetActivity = Permit3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "点击事件触发申请单个固定权限"
            project.targetActivity = Permit4Activity::class.java
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

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}