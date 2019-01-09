package com.jc.flora.apps.scene.qrcode

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.qrcode.projects.Qrcode1Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by Shijincheng on 2018/10/24.
 */
class QrcodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "二维码"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "生成与识别二维码"
            project.targetActivity = Qrcode1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "扫描二维码"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
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