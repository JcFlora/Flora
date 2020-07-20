package com.jc.flora.apps.ui.literacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.literacy.projects.Literacy1Activity
import com.jc.flora.apps.ui.typeface.projects.PhoneticPinyin1Activity
import com.jc.flora.apps.ui.typeface.projects.PhoneticPinyin2Activity
import java.util.*

/**
 * Created by shijincheng on 2018/1/21.
 */
class LiteracyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "拼音识字"
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
            return arrayListOf(project1, project2, project3)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "识字0.1：居中对齐"
            project.targetActivity = Literacy1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "识字0.2：居左对齐"
            project.targetActivity = PhoneticPinyin1Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "识字0.3：选词填空"
            project.targetActivity = PhoneticPinyin2Activity::class.java
            return project
        }

}