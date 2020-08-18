package com.jc.flora.apps.scene.identity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.identity.projects.Identity1Activity
import com.jc.flora.apps.scene.identity.projects.Identity2Activity
import com.jc.flora.apps.scene.identity.projects.Identity3Activity
import com.jc.flora.apps.scene.identity.projects.Identity4Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2020/7/21.
 */
class IdentityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "身份验证"
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
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16, project17, project18, project19)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.1：普通用户登录验证"
            project.targetActivity = Identity1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.2：单页面登录检测+使用登录动作拦截器"
            project.targetActivity = Identity2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.3：使用登录状态监听器实现多页面登录状态同步"
            project.targetActivity = Identity3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.4：合并单页面登录拦截和多页面登录状态同步，并封装为Lander框架"
            project.targetActivity = Identity4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.5：各种第三方登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.6：VIP身份拦截"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "身份验证0.7：VIP和普通会员合并为Identifier框架"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = ""
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
