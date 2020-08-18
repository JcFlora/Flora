package com.jc.flora.apps.scene.bluetooth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jc.flora.R
import com.jc.flora.apps.Project
import com.jc.flora.apps.ProjectsAdapter
import com.jc.flora.apps.scene.bluetooth.projects.*
import com.jc.flora.launcher.NotFoundActivity
import java.util.*

/**
 * Created by shijincheng on 2017/2/28.

 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 * uses-feature android:name="android.hardware.bluetooth_le" android:required="false"
 * uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
 * uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"
 */
class BluetoothActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "蓝牙"
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
                    project11, project12, project13)
        }

    private val project1: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.1：打开蓝牙"
            project.targetActivity = Bluetooth1Activity::class.java
            return project
        }

    private val project2: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.2：监控蓝牙开关状态"
            project.targetActivity = Bluetooth2Activity::class.java
            return project
        }

    private val project3: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.3：封装开关按钮"
            project.targetActivity = Bluetooth3Activity::class.java
            return project
        }

    private val project4: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.4：获取已配对设备"
            project.targetActivity = Bluetooth4Activity::class.java
            return project
        }

    private val project5: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.5：动态获取已配对设备"
            project.targetActivity = Bluetooth5Activity::class.java
            return project
        }

    private val project6: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.6：封装已配对设备列表"
            project.targetActivity = Bluetooth6Activity::class.java
            return project
        }

    private val project7: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.7：扫描可用设备"
            project.targetActivity = Bluetooth7Activity::class.java
            return project
        }

    private val project8: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.8：添加扫描中进度条"
            project.targetActivity = Bluetooth8Activity::class.java
            return project
        }

    private val project9: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙0.9：设置可检测性"
            project.targetActivity = Bluetooth9Activity::class.java
            return project
        }

    private val project10: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙1.0：添加刷新按钮"
            project.targetActivity = Bluetooth10Activity::class.java
            return project
        }

    private val project11: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙1.1：配对与取消配对"
            project.targetActivity = Bluetooth11Activity::class.java
            return project
        }

    private val project12: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙1.2：监听配对状态变化"
            project.targetActivity = Bluetooth12Activity::class.java
            return project
        }

    private val project13: Project
        get() {
            val project = Project()
            project.projectName = "蓝牙1.3：蓝牙连接"
            project.targetActivity = NotFoundActivity::class.java
            return project
        }

}