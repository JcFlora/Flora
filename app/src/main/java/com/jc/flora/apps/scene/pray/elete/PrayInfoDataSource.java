package com.jc.flora.apps.scene.pray.elete;

/**
 * 置灰状态数据源
 * Created by shijincheng on 2020/9/18.
 */
public interface PrayInfoDataSource {
    boolean readPrayStatus();
    void writePrayStatus(boolean isPrayOn);
}