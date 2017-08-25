package com.jc.flora.apps.scene.login.api;

/**
 * Created by shijincheng on 2017/8/25.
 */
public class GetVerCodeResponse {

    /** 接口返回数据成功标记 */
    public boolean success;
    /** 接口返回数据提示信息 */
    public String msg;

    public GetVerCodeResponse(){}

    public GetVerCodeResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
