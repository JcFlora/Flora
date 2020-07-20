package com.jc.flora.apps.component.cache

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.cache.projects.Cacher1Activity
import com.jc.flora.apps.component.cache.projects.Cacher2Activity
import com.jc.flora.apps.component.cache.projects.Spreader1Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/1/25.
 */
class CacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "本地缓存"
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
                    project6, project7)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "基本数据类型的静态变量缓存"
            project.targetActivity = Cacher1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "实体数据类型的静态变量缓存"
            project.targetActivity = Cacher2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "基本数据类型的sp缓存"
            project.targetActivity = Spreader1Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "实体数据类型的sp缓存"
            project.targetActivity = Spreader1Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "单例sp缓存"
            project.targetActivity = Spreader1Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "带版本号的sp缓存"
            project.targetActivity = Spreader1Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "Glide的图片缓存"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}