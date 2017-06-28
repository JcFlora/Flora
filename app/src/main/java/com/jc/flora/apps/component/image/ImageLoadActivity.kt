package com.jc.flora.apps.component.image

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.dialog.projects.AlertDialogActivity
import com.jc.flora.apps.ui.dialog.projects.BottomSheetDialogActivity
import com.jc.flora.apps.ui.dialog.projects.ProgressDialogActivity
import com.jc.flora.apps.ui.dialog.projects.SnackBarShowActivity
import com.jc.flora.apps.ui.dialog.projects.SystemDialogActivity
import com.jc.flora.apps.ui.dialog.projects.ToastActivity
import com.jc.flora.apps.component.image.projects.GifActivity

import java.util.ArrayList

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
        val rvProjects = findViewById(R.id.rv_projects) as RecyclerView
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
            project.projectName = "加载Gif"
            project.targetActivity = GifActivity::class.java
            return project
        }

}