package com.jc.flora.apps.ui.typeface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.typeface.projects.PhoneticIpaActivity
import com.jc.flora.apps.ui.typeface.projects.PhoneticPinyin1Activity
import com.jc.flora.apps.ui.typeface.projects.PhoneticPinyin2Activity
import java.util.*

/**
 * Created by shijincheng on 2017/9/11.
 */
class TypefaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "特殊字库"
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
            project.projectName = "显示英文国际音标（基于字库）"
            project.targetActivity = PhoneticIpaActivity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "显示汉语拼音（单独输入）"
            project.targetActivity = PhoneticPinyin1Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "显示带拼音文字（基于字库）"
            project.targetActivity = PhoneticPinyin2Activity::class.java
            return project
        }

}