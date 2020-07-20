package com.jc.flora.apps.component.signal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.signal.projects.Signal1Activity
import com.jc.flora.apps.component.signal.projects.Signal2Activity
import com.jc.flora.apps.component.signal.projects.Signal3Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2019/1/16.
 */
class SignalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "组件通信"
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
                    project6, project7, project8, project9)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "通信0.1：setResult实现Activity组件通信"
            project.targetActivity = Signal1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "通信0.2：封装setResult逻辑为Delegate"
            project.targetActivity = Signal2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "通信0.3：通过回调实现Fragment的通信"
            project.targetActivity = Signal3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "通信0.4：通过所属Activity读写数据实现Fragment的数据通信"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "通信0.5：本地广播实现Activity组件通信"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "通信0.6：封装本地广播为无痕广播"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "通信0.7：本地广播实现和Service组件通信"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "通信0.8：使用全局Handler实现组件通信"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "通信0.9：使用EventBus3"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}