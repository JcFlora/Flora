package com.jc.flora.apps.scene.login.api;

/**
 * Created by shijincheng on 2017/5/19.
 */
public class LoginResponse {

    /** 接口返回数据成功标记 */
    public boolean success;
    /** 接口返回数据提示信息 */
    public String msg;

    public LoginResponse(){}

    public LoginResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
