package com.jc.flora.apps.scene.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.search.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "搜索商品"
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
                    project6, project7)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.1：单页面搜索"
            project.targetActivity = Search1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.2：添加外部搜索入口"
            project.targetActivity = Search2EntryActivity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.3：搜索主页面和结果页分离"
            project.targetActivity = Search3EntryActivity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.4：主页面添加历史词功能"
            project.targetActivity = Search4EntryActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.5：主页面添加热搜词功能"
            project.targetActivity = Search5EntryActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.6：主页面添加联想词功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "搜索0.7：主页面和结果页使用Fragment实现"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
