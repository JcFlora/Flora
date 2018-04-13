package com.jc.flora.apps.component.ndk.delegate;

/**
 * Created by Shijincheng on 2018/4/13.
 */

public class NativeDelegate2 {

    /*
     * 源码形式编译Lame动态库的步骤：
     *
     * 1、下载Lame库源码，下载完后解压；
     *
     * 2、在main路径下创建cpp路径；
     *
     * 3、将Lame的源码解压后，把libmp3lame文件夹下除了.h和.c的文件都去掉，vector和i386文件夹也都去掉；
     *    并将在libmp3lame里剩下的文件，都复制到cpp路径下；
     *    同时还要将lame-3.99.5\include\lame.h这个头文件也复制过去；
     *    为了好管理，在cpp路径下新建一个文件夹lame把这些源码放在一起；
     *
     * 4、修改Lame源码，见https://www.jianshu.com/p/065bfe6d3ec2；
     *
     * 5、编辑CmakeList.txt文件，添加Lame编译部分；
     *
     * 6、jni层调用Lame库，添加#include "lame.h"即可。
     */

    static {
        System.loadLibrary("lame-1");
    }

    public native String getLameVersion();

}