#CMake的最低版本号
cmake_minimum_required(VERSION 3.4.1)

#生成so库的名称及jni层代码的文件位置
add_library(jni-test1 SHARED src/main/jni/jni-test1.cpp)