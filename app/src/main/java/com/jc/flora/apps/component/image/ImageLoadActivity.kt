package com.jc.flora.apps.component.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.image.projects.Gif1Activity
import com.jc.flora.apps.component.image.projects.Gif2Activity
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class ImageLoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "图片加载"
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
            return arrayListOf(project1, project2)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "使用GifDrawable加载Gif"
            project.targetActivity = Gif1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "使用Glide加载Gif"
            project.targetActivity = Gif2Activity::class.java
            return project
        }

}