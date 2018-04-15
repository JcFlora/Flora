package com.jc.flora.apps.component.ndk.delegate;

/**
 * Created by Shijincheng on 2018/4/13.
 */

public class NativeDelegate1 {

    /*
     * Android Studio中NDK开发步骤：
     *
     * 1、SDK Manager中下载Cmake、LLDB和NDK；
     *
     * 2、创建jni调用类，如本类；
     *
     * 3、创建native方法，如本类中的getStringFromJni方法；
     *
     * 4、创建静态代码块，加载动态库，如本类中的
     *      System.loadLibrary("jni-test1");
     *
     * 5、在main路径下创建jni路径，再创建jni层代码文件，如本项目中的jni-test1.cpp，在里面添加如下代码：
     *      #include <jni.h>
     *
     * 6、在app目录下创建CMakeLists.txt文件；
     *
     * 7、在CMakeLists.txt文件中添加cmake的最低版本号
     *      cmake_minimum_required(VERSION 3.4.1)
     *
     * 8、在CMakeLists.txt文件中添加生成so库的名称及jni层代码的文件位置
     *      add_library(jni-test1 SHARED src/main/jni/jni-test1.cpp)
     *
     * 9、在CMakeLists.txt文件上右键选择Link C++ Project With Gradle；
     *
     * 10、之后在弹出的对话框中，选择CMakeLists.txt的位置，点击OK，等待系统编译；
     *
     * 11、编译完后，build.gradle文件中多出如下配置
     *     externalNativeBuild {
     *         cmake {
     *             path 'CMakeLists.txt'
     *         }
     *     }
     *
     * 12、如果需要生成指定平台的so库，在build.gradle文件中添加如下配置
     *         externalNativeBuild {
     *            cmake {
     *                cppFlags ""
     *                abiFilters 'armeabi-v7a', 'arm64-v8a', 'x86', 'x86_64'
     *            }
     *        }
     *
     * 12、clean项目，之后在native方法上点击提示，创建jni层对应的代码；
     *
     * 13、修改jni层的代码，如：
     * #include <jni.h>
     * #include <string>
     *
     * extern "C"
     * JNIEXPORT jstring JNICALL
     * Java_com_jc_flora_apps_component_ndk_delegate_NativeDelegate1_getStringFromJni(JNIEnv *env,
     *                                                                                jobject ) {
     *     std::string hello = "我来自JNI层";
     *     return env->NewStringUTF(hello.c_str());
     * }
     *
     * 14、最后在项目中调用native方法即可。
     *
     */

    static {
        System.loadLibrary("jni-test1");
    }

    public native String getStringFromJni();

    public native String callJavaMethod();

}
