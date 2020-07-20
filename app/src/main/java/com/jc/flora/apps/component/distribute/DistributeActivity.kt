package com.jc.flora.apps.component.distribute

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.distribute.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by Shijincheng on 2018/9/1.
 */
class DistributeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "分发代理"
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
                    project6, project7, project8, project9, project10)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "分发一个简单的业务功能"
            project.targetActivity = Distribute1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "分发一个需要同步生命周期的业务功能"
            project.targetActivity = Distribute2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "通过回调实现业务代理之间的关联"
            project.targetActivity = Distribute3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "通过注入实现业务代理之间的关联"
            project.targetActivity = Distribute4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "使用Fragment实现自动同步生命周期的业务代理"
            project.targetActivity = Distribute5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "处理Fragment代理默认缓存恢复引起的问题"
            project.targetActivity = Distribute6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "处理Fragment代理异步加载引起的问题"
            project.targetActivity = Distribute7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "Fragment代理中使用ViewHolder"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "在Fragment中使用Fragment代理"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "抽取父类"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}