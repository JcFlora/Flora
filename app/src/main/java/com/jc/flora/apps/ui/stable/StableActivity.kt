package com.jc.flora.apps.ui.stable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.stable.projects.*
import java.util.*

/**
 * Created by shijincheng on 2017/3/6.
 */
class StableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "沉浸模式"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6,project7, project8, project9)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "透明状态栏"
            project.targetActivity = Stable1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "状态栏着色"
            project.targetActivity = Stable2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "实时切换：透明+（伪）着色"
            project.targetActivity = Stable3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "实时切换：透明+（伪）渐显"
            project.targetActivity = Stable4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "浮动标题（监听滑动）"
            project.targetActivity = Stable5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "浮动标题（监听滑动+动画）"
            project.targetActivity = Stable6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "浮动标题（使用AppBarLayout）"
            project.targetActivity = Stable7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "浮动标题、导航、FAB（平移动画）"
            project.targetActivity = Stable8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "浮动标题、导航、FAB（缩放动画）"
            project.targetActivity = Stable9Activity::class.java
            return project
        }

}