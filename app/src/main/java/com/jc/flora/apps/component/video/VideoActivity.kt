package com.jc.flora.apps.component.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.audio.projects.*
import com.jc.flora.apps.component.video.projects.*
import java.util.*

/**
 * Created by shijincheng on 2018/8/23.
 */
class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "播放视频"
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
                    project16, project17, project18, project19, project20)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "视频0.1：使用VideoView播放本地MP4"
            project.targetActivity = Video1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "视频0.2：自定义播放暂停按钮"
            project.targetActivity = Video2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "视频0.3：实现自定义控制层的动态隐藏"
            project.targetActivity = Video3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "视频0.4：自定义进度显示和控制"
            project.targetActivity = Video4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "视频0.5：全屏显示"
            project.targetActivity = Video5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "视频0.6：单个视频的全屏/小屏切换"
            project.targetActivity = Video6Activity::class.java
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
            project.targetActivity = Audio19Activity::class.java
            return project
        }

    private val project20: Project
        get() {
            val project = Project()
            project.projectName = "音频2.0：使用ExoPlayer播放本地MP3列表"
            project.targetActivity = Audio20Activity::class.java
            return project
        }

}