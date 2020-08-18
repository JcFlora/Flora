package com.jc.flora.apps.scene.identity.lander;

/**
 * 登录动作响应
 * Created by shijincheng on 2020/6/29.
 */
public interface LoginActionCallback {
    void isLoggedIn();
    void onLoginSuccess();
    void onLoginCancel();
    void onLogoutSuccess();
}