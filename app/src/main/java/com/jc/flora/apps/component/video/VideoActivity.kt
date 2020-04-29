package com.jc.flora.apps.component.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.component.video.projects.*
import com.jc.flora.launcher.NotFoundActivity
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
                    project16, project17, project18, project19, project20,
                    project21, project22, project23, project24, project25,
                    project26, project27, project28, project29, project30,
                    project31, project32, project33)
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
            project.projectName = "视频0.6：单个视频的全半屏切换"
            project.targetActivity = Video6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "视频0.7：添加状态监听实现控制反转"
            project.targetActivity = Video7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "视频0.8：添加快进快退手势浮层"
            project.targetActivity = Video8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "视频0.9：添加亮度控制手势浮层"
            project.targetActivity = Video9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "视频1.0：添加音量控制手势浮层"
            project.targetActivity = Video10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "视频1.1：添加封面浮层"
            project.targetActivity = Video11Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "视频1.2：使用MediaPlayer+TextureView播放"
            project.targetActivity = Video12Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "视频1.3：支持多个视频切换播放"
            project.targetActivity = Video13Activity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "视频1.4：支持视频容器的动态切换+全半屏切换优化"
            project.targetActivity = Video14Activity::class.java
            return project
        }

    private val project15: Project
        get() {
            val project = Project()
            project.projectName = "视频1.5：实现列表播放"
            project.targetActivity = Video15Activity::class.java
            return project
        }

    private val project16: Project
        get() {
            val project = Project()
            project.projectName = "视频1.6：实现滑动到屏幕外自动暂停"
            project.targetActivity = Video16Activity::class.java
            return project
        }

    private val project17: Project
        get() {
            val project = Project()
            project.projectName = "视频1.7：列表播放的全半屏切换"
            project.targetActivity = Video17Activity::class.java
            return project
        }

    private val project18: Project
        get() {
            val project = Project()
            project.projectName = "视频1.8：列表全半屏切换时动态控制手势浮层可用"
            project.targetActivity = Video18Activity::class.java
            return project
        }

    private val project19: Project
        get() {
            val project = Project()
            project.projectName = "视频1.9：播放在线视频"
            project.targetActivity = Video19Activity::class.java
            return project
        }

    private val project20: Project
        get() {
            val project = Project()
            project.projectName = "视频2.0：添加loading浮层和缓冲进度"
            project.targetActivity = Video20Activity::class.java
            return project
        }

    private val project21: Project
        get() {
            val project = Project()
            project.projectName = "视频2.1：添加完成播放浮层"
            project.targetActivity = Video21Activity::class.java
            return project
        }

    private val project22: Project
        get() {
            val project = Project()
            project.projectName = "视频2.2：添加错误浮层与无网络检测功能"
            project.targetActivity = Video22Activity::class.java
            return project
        }

    private val project23: Project
        get() {
            val project = Project()
            project.projectName = "视频2.3：添加移动网络检测与视频容量显示功能"
            project.targetActivity = Video23Activity::class.java
            return project
        }

    private val project24: Project
        get() {
            val project = Project()
            project.projectName = "视频2.4：抽离MediaPlayer播放引擎"
            project.targetActivity = Video24Activity::class.java
            return project
        }

    private val project25: Project
        get() {
            val project = Project()
            project.projectName = "视频2.5：使用ExoPlayer播放引擎"
            project.targetActivity = Video25Activity::class.java
            return project
        }

    private val project26: Project
        get() {
            val project = Project()
            project.projectName = "视频2.6：使用IjkPlayer播放引擎"
            project.targetActivity = Video26Activity::class.java
            return project
        }

    private val project27: Project
        get() {
            val project = Project()
            project.projectName = "视频2.7：锁定/解锁全屏点击功能"
            project.targetActivity = Video27Activity::class.java
            return project
        }

    private val project28: Project
        get() {
            val project = Project()
            project.projectName = "视频2.8：实现试看功能+试看图层"
            project.targetActivity = Video28Activity::class.java
            return project
        }

    private val project29: Project
        get() {
            val project = Project()
            project.projectName = "视频2.9：视频焦点处理"
            project.targetActivity = Video29Activity::class.java
            return project
        }

    private val project30: Project
        get() {
            val project = Project()
            project.projectName = "视频3.0：实现倍速播放"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project31: Project
        get() {
            val project = Project()
            project.projectName = "视频3.1：列表+详情双页同步播放"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project32: Project
        get() {
            val project = Project()
            project.projectName = "视频3.2：添加共享元素转场动画"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project33: Project
        get() {
            val project = Project()
            project.projectName = "视频3.3：详情页滚动时独立浮动小窗播放"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }
}