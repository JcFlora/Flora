package com.jc.flora.apps.scene.pray.api;

/**
 * Created by shijincheng on 2020/9/21.
 */
public class PrayResponse {

    /** 接口返回数据成功标记 */
    public boolean success;
    /** 接口返回数据提示信息 */
    public String msg;

    public PrayResponse(){}

    public PrayResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
