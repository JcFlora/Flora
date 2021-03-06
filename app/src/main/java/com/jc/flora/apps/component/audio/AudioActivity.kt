package com.jc.flora.apps.component.audio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val rvProjects: RecyclerView = findViewById(R.id.rv_projects)
        rvProjects.layoutManager = LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        rvProjects.adapter = ProjectsAdapter(this, projects)
    }

    private val projects: ArrayList<Project>
        get() {
            return arrayListOf(project1, project2, project3, project4, project5,
                    project6, project7, project8, project9, project10,
                    project11, project12, project13, project14, project15,
                    project16, project17, project18, project19, project20,
                    project21, project22, project23, project24, project25,
                    project26, project27, project28, project29, project30,
                    project31, project32, project33, project34, project35,
                    project36, project37, project38)
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

    private val project21: Project
        get() {
            val project = Project()
            project.projectName = "音频2.1：列表页面播放本地MP3"
            project.targetActivity = AudioList21Activity::class.java
            return project
        }

    private val project22: Project
        get() {
            val project = Project()
            project.projectName = "音频2.2：列表+详情双页同步播放"
            project.targetActivity = AudioList22Activity::class.java
            return project
        }

    private val project23: Project
        get() {
            val project = Project()
            project.projectName = "音频2.3：列表添加播放状态显示"
            project.targetActivity = AudioList23Activity::class.java
            return project
        }

    private val project24: Project
        get() {
            val project = Project()
            project.projectName = "音频2.4：添加快进快退15秒功能"
            project.targetActivity = AudioList24Activity::class.java
            return project
        }

    private val project25: Project
        get() {
            val project = Project()
            project.projectName = "音频2.5：添加倍速播放功能"
            project.targetActivity = AudioList25Activity::class.java
            return project
        }

    private val project26: Project
        get() {
            val project = Project()
            project.projectName = "音频2.6：播放在线音频"
            project.targetActivity = AudioList26Activity::class.java
            return project
        }

    private val project27: Project
        get() {
            val project = Project()
            project.projectName = "音频2.7：添加loading和缓冲回调处理"
            project.targetActivity = AudioList27Activity::class.java
            return project
        }

    private val project28: Project
        get() {
            val project = Project()
            project.projectName = "音频2.8：模拟支付拦截和url异步获取拦截"
            project.targetActivity = AudioList28Activity::class.java
            return project
        }

    private val project29: Project
        get() {
            val project = Project()
            project.projectName = "音频2.9：添加无网络拦截与错误回调"
            project.targetActivity = AudioList29Activity::class.java
            return project
        }

    private val project30: Project
        get() {
            val project = Project()
            project.projectName = "音频3.0：添加移动网络检测与音频容量显示功能"
            project.targetActivity = AudioList30Activity::class.java
            return project
        }

    private val project31: Project
        get() {
            val project = Project()
            project.projectName = "音频3.1：抽离ExoPlayer播放引擎"
            project.targetActivity = AudioList31Activity::class.java
            return project
        }

    private val project32: Project
        get() {
            val project = Project()
            project.projectName = "音频3.2：使用IjkPlayer播放引擎"
            project.targetActivity = AudioList32Activity::class.java
            return project
        }

    private val project33: Project
        get() {
            val project = Project()
            project.projectName = "音频3.3：实现断点续播功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project34: Project
        get() {
            val project = Project()
            project.projectName = "音频3.4：实现播放最近一首功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project35: Project
        get() {
            val project = Project()
            project.projectName = "音频3.5：实现离线播放功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project36: Project
        get() {
            val project = Project()
            project.projectName = "音频3.6：实现音质切换功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project37: Project
        get() {
            val project = Project()
            project.projectName = "音频3.7：添加定时停止功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project38: Project
        get() {
            val project = Project()
            project.projectName = "音频3.8：封装为Aoide框架"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}