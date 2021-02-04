package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 升级器检测升级的桥接接口
 * Created by shijincheng on 2021/1/28.
 */
public interface RequestCheckVersionNetBridge {
    /**
     * 实现此方法，调用网络请求，并在结果中调用callback继续执行后续流程
     * @param callback
     */
    void requestCheckVersion(RequestCheckVersionNetCallback callback);
}