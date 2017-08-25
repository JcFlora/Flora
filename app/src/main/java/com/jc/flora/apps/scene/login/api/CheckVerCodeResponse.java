package com.jc.flora.apps.scene.login.api;

/**
 * Created by shijincheng on 2017/8/25.
 */
public class CheckVerCodeResponse {

    /** 接口返回数据成功标记 */
    public boolean success;
    /** 接口返回数据提示信息 */
    public String msg;

    public CheckVerCodeResponse(){}

    public CheckVerCodeResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
