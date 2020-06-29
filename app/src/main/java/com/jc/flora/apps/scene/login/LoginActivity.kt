package com.jc.flora.apps.scene.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.login.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "登录界面"
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
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16, project17, project18, project19)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "登录0.1：最简单登录"
            project.targetActivity = Login1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "登录0.2：模拟接口校验"
            project.targetActivity = Login2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "登录0.3：添加本地校验"
            project.targetActivity = Login3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "登录0.4：添加单页面登录结果回调"
            project.targetActivity = Login4TestActivity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "登录0.5：单页面登录检测+使用登录动作拦截器"
            project.targetActivity = Login5TestActivity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "登录0.6：控制密码是否可见"
            project.targetActivity = Login6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "登录0.7：清空输入内容功能"
            project.targetActivity = Login7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "登录0.8：控制登录按钮是否可用(使用RxJava)"
            project.targetActivity = Login8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "登录0.9：验证码1(基于CountDownTimer)"
            project.targetActivity = Login9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "登录1.0：验证码2(使用RxJava)"
            project.targetActivity = Login10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "登录1.1：使用登录状态监听器实现多页面登录状态同步"
            project.targetActivity = Login11TestActivity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "登录1.2：合并单页面登录拦截和多页面登录状态同步，并封装为Lander框架"
            project.targetActivity = Login12TestActivity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "登录1.3：使用官方TextInputLayout实现"
            project.targetActivity = Login13Activity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "登录1.4：邮箱自动补全"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "登录1.5："
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "登录1.6：QQ第三方登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = "登录1.7：仿QQ登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = "登录1.8：仿哔哩哔哩登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = "登录1.9：隔离出LoginUiDelegate"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
