package com.jc.flora.apps.scene.login.lander;

/**
 * 登录信息数据源
 * Created by shijincheng on 2020/6/29.
 */
public interface LoginInfoDataSource {
    void writeLoginStatus(boolean isLogin);
    boolean readLoginStatus();
}
