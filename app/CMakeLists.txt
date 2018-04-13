#CMake的最低版本号
cmake_minimum_required(VERSION 3.4.1)

#生成so库的名称及jni层代码的文件位置
add_library(jni-test1 SHARED src/main/jni/jni-test1.cpp)


#设置变量SRC_DIR为lame的所在路径
set(SRC_DIR src/main/cpp/lame)

#指定头文件所在，可以多次调用，指定多个路径
include_directories(src/main/cpp/lame)

#添加自自定义的so库时，有两种方式，一种添加一个目录，一种一个个文件添加，这里使用目录的方式

#设定一个目录
aux_source_directory(src/main/cpp/lame SRC_LIST)

#将前面目录下所有的文件都添加进去
add_library(lame-1 SHARED src/main/jni/lame-1.cpp ${SRC_LIST})