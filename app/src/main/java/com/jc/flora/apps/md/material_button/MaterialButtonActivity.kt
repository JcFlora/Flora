package com.jc.flora.apps.md.material_button

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.md.toolbar.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2020/9/11.
 */
class MaterialButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "MaterialButton"
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
                    project6, project7, project8, project9, project10, project11)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "圆角按钮"
            project.targetActivity = Toolbar1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "椭圆/圆形按钮"
            project.targetActivity = Toolbar2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "渐变背景按钮"
            project.targetActivity = Toolbar3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "高亮反馈、水波纹反馈"
            project.targetActivity = Toolbar4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "带图标的按钮"
            project.targetActivity = Toolbar5Activity::class.java
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

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
