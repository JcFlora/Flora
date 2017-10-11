package com.jc.flora.apps.component.audio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.audio.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/7/14.
 */
class AudioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "播放音频"
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
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "音频0.1：使用MediaPlayer播放本地MP3"
            project.targetActivity = Audio1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "音频0.2：后台Service播放本地MP3"
            project.targetActivity = Audio2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "音频0.3：支持暂停和继续播放"
            project.targetActivity = Audio3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "音频0.4：支持进度显示和控制"
            project.targetActivity = Audio4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "音频0.5：支持多个MP3切换播放"
            project.targetActivity = Audio5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "音频0.6：支持上一首／下一首功能"
            project.targetActivity = Audio6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "音频0.7：实现列表循环播放"
            project.targetActivity = Audio7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "音频0.8：添加状态监听实现控制反转"
            project.targetActivity = Audio8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "音频0.9：实现自动播放下一首"
            project.targetActivity = Audio9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "音频1.0：实现三种播放模式切换"
            project.targetActivity = Audio10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "音频1.1：经典控制播放按钮"
            project.targetActivity = Audio11Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "音频1.2：实现通知栏显示和交互"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "音频1.3：实现锁屏显示和交互"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "音频1.4：音频焦点处理"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "音频1.5：耳机线控处理"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "音频1.6：使用ExoPlayer播放本地MP3"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}