package com.jc.flora.apps.scene.album

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.album.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2019/4/6.
 */
class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "图片选择"
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
                    project11, project12, project13, project14)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "图片选择0.1：使用系统相册"
            project.targetActivity = Album1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "图片选择0.2：使用系统拍照"
            project.targetActivity = Album2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "图片选择0.3：添加图片压缩功能"
            project.targetActivity = Album3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "图片选择0.4：添加系统裁剪功能"
            project.targetActivity = Album4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "图片选择0.5：列表展示选择结果+大图预览"
            project.targetActivity = Album5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.1：Matisse实现单选功能"
            project.targetActivity = Matisse1Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.2：Matisse实现拍照功能"
            project.targetActivity = Matisse2Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.3：Matisse实现多选功能"
            project.targetActivity = Matisse3Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.4：自定义Matisse主题"
            project.targetActivity = Matisse4Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.5：结合Luban实现批量压缩"
            project.targetActivity = Matisse5Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.6：结合Ucrop实现单个裁剪"
            project.targetActivity = Matisse6Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.7：结合Ucrop实现批量裁剪"
            project.targetActivity = Matisse7Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.8：整合摄像头权限管理"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

    private val project14: Project
        get() {
            val project = Project()
            project.projectName = "Matisse0.9：模拟头像上传功能"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}