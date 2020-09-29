package com.jc.flora.apps.md.shapeable_image_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.md.shapeable_image_view.projects.ShapeableImageView1Activity
import java.util.*

/**
 * Created by shijincheng on 2020/9/23.
 */
class ShapeableImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "ShapeableImageView"
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
            return arrayListOf(project1)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "各种裁剪的图片"
            project.targetActivity = ShapeableImageView1Activity::class.java
            return project
        }

}