package com.jc.flora.apps.scene.guider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.guider.projects.*
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class GuiderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "引导界面"
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
            return arrayListOf(project1, project2, project3, project4, project5)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "引导界面0.1：最简单引导页"
            project.targetActivity = Guider1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "引导界面0.2：隔离出GuiderDelegate"
            project.targetActivity = Guider2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "引导界面0.3：特色指示器"
            project.targetActivity = Guider3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "引导界面0.4：MD风格引导页"
            project.targetActivity = Guider4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "引导界面0.5：小红书引导页"
            project.targetActivity = GuiderXhsActivity::class.java
            return project
        }

}