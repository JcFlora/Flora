//
// Created by Shijincheng on 2018/4/13.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_jc_flora_apps_component_ndk_delegate_NativeDelegate1_getStringFromJni(JNIEnv *env,
                                                                               jobject ) {
    std::string hello = "我来自JNI层";
    return env->NewStringUTF(hello.c_str());
}