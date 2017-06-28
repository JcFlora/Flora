package com.jc.flora.apps.ui.badge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.ui.badge.projects.OvalBadgeActivity
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.
 */
class BadgeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "消息角标"
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
            project.projectName = "腰圆角标"
            project.targetActivity = OvalBadgeActivity::class.java
            return project
        }

    //    private Project getProject2(){
    //        Project project = new Project();
    //        project.projectName = "页面导航0.2：导航按钮状态切换";
    //        project.targetActivity = Captain2Activity.class;
    //        return project;
    //    }
    //
    //    private Project getProject3(){
    //        Project project = new Project();
    //        project.projectName = "页面导航0.3：封装导航条";
    //        project.targetActivity = Captain3Activity.class;
    //        return project;
    //    }
    //
    //    private Project getProject4(){
    //        Project project = new Project();
    //        project.projectName = "页面导航0.4：动态添加指示器item";
    //        project.targetActivity = Captain4Activity.class;
    //        return project;
    //    }

}