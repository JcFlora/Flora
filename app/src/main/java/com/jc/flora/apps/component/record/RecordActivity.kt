package com.jc.flora.apps.component.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.audio.projects.*
import com.jc.flora.apps.component.record.projects.Record1Activity
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/11/18.
 */
class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "录制音频"
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
            project.projectName = "录制音频0.1：使用MediaRecorder录制AAC格式音频"
            project.targetActivity = Record1Activity::class.java
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
            project.projectName = "音频1.2：封装AudioPlayerDelegate"
            project.targetActivity = Audio12Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "音频1.3：实现通知栏显示和交互"
            project.targetActivity = Audio13Activity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "音频1.4：实现通知栏大UI"
            project.targetActivity = Audio14Activity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "音频1.5：实现锁屏时显示封面"
            project.targetActivity = Audio15Activity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "音频1.6：音频焦点处理"
            project.targetActivity = Audio16Activity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = "音频1.7：实现耳机拔出暂停播放"
            project.targetActivity = Audio17Activity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = "音频1.8：实现界面上的音量控制"
            project.targetActivity = Audio18Activity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = "音频1.9：使用ExoPlayer播放本地MP3"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}