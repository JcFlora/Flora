//
// Created by Shijincheng on 2018/4/13.
//

#include <jni.h>
#include "lame.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_jc_flora_apps_component_ndk_delegate_NativeDelegate2_getLameVersion(JNIEnv *env,
                                                                             jobject ) {
    return env->NewStringUTF(get_lame_version());
}
