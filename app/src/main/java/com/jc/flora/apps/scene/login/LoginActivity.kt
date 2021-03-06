package com.jc.flora.apps.scene.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        rvProjects.layoutManager = LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16)
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
            project.projectName = "登录0.4：控制密码是否可见"
            project.targetActivity = Login6Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "登录0.5：清空输入内容功能"
            project.targetActivity = Login7Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "登录0.6：控制登录按钮是否可用(使用RxJava)"
            project.targetActivity = Login8Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "登录0.7：验证码1(基于CountDownTimer)"
            project.targetActivity = Login9Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "登录0.8：验证码2(使用RxJava)"
            project.targetActivity = Login10Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "登录0.9：使用官方TextInputLayout实现"
            project.targetActivity = Login13Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "登录1.0：邮箱自动补全"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "登录1.1：仿QQ登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "登录1.2：仿哔哩哔哩登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "登录1.3：隔离出LoginUiDelegate"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "登录1.4：实现QQ登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "登录1.5：实现微信登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "登录1.6：实现手机号一键认证登录"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}
