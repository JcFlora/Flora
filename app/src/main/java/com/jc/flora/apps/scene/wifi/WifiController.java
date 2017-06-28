//package com.jc.flora.apps.scene.wifi;
//
//import android.content.Context;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.lang.reflect.Method;
//import java.util.List;
//
///**
// * Created by shijincheng on 2017/5/2.
// * 这个类负责wifi的扫描,连接,断开
// * 判断wifi是否可用
// * uses-permission android:name="android.permission.ACCESS_WIFI_STATE"
// * uses-permission android:name="android.permission.CHANGE_WIFI_STATE"
// * uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"
// * uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"
// */
//public class WifiController {
//
//    private static final String TAG = "WifiController";
//    private final WifiManager mWifiManager;
//    private final Context mContext;
//    private OnWifiConnectListener mOnWifiConnectListener;
//
//    private WifiController(Context context) {
//        //拿到wifi管理器
//        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        mContext = context.getApplicationContext();
//    }
//
//    private static WifiController sInstant = null;
//
//    /**
//     * 获取 一个单例对象
//     *
//     * @param context 上下文
//     * @return 返回实例对象
//     */
//    public static WifiController getInstant(Context context) {
//        if (sInstant == null) {
//            synchronized (WifiController.class) {
//                if (sInstant == null) {
//                    sInstant = new WifiController(context);
//                }
//            }
//        }
//        return sInstant;
//    }
//
//    /**
//     * 这个方法用于扫描周围的wifi
//     */
//    public void scanWifiAround() {
//        if (!isWifiEnable()) {
//            Toast.makeText(mContext, "wifi没有打开嘛...", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        mWifiManager.startScan();
//    }
//
//    /**
//     * @return true表示wifi已经打开, false表示wifi没有打开, 状态为:
//     * 打开中,或者关闭,或者关闭中...
//     */
//    public boolean isWifiEnable() {
//        return mWifiManager.isWifiEnabled();
//    }
//
//    /**
//     * 这个方法用于打开或关闭wifi
//     * 当wifi打开的时候,那么就会关闭wifi
//     * 当wifi关闭的时候,那么就会打开wifi
//     */
//    public void openOrCloseWifi() {
//
//        //判断当前wifi的状态,是关闭还是打开
//        if (this.isWifiEnable()) {
//            mWifiManager.setWifiEnabled(false);
//        } else {
//            mWifiManager.setWifiEnabled(true);
//        }
//    }
//
//    /**
//     * @return 返回扫描的wifi结果, 是一个list集合
//     * @call 当收到扫描结果的广播以后就可以调用这个方法去获取扫描结果
//     */
//    public List<ScanResult> getWifiScanResult() {
//        return mWifiManager.getScanResults();
//    }
//
//    /**
//     * @param SSID 这个是wifi的SSID
//     * @return true表示已经存在了, 否则表示不存在
//     */
//
//    /**
//     * 判断配置在系统中是否存在
//     *
//     * @param config 新的配置
//     * @return 配置存在就更新配置，把新的配置返回，配置不存在就返回null
//     */
//    private WifiConfiguration isExists(WifiConfiguration config) {
//        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
//        for (WifiConfiguration existingConfig : existingConfigs) {
//            Log.i(TAG, "系统保存配置的 SSID : " + existingConfig.SSID + "  networkId : " + existingConfig.networkId);
//            if (existingConfig.SSID.equals(config.SSID)) {
//                config.networkId = existingConfig.networkId;
//                return config;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取NetworkId
//     *
//     * @param scanResult 扫描到的WIFI信息
//     * @return 如果有配置信息则返回配置的networkId 如果没有配置过则返回-1
//     */
//    public int getNetworkIdFromConfig(ScanResult scanResult) {
//        String SSID = String.format("\"%s\"", scanResult.SSID);
//        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
//
//        for (WifiConfiguration existingConfig : existingConfigs) {
//            if (existingConfig.SSID.equals(SSID)) {
//                return existingConfig.networkId;
//            }
//        }
//
//        return -1;
//    }
//
//
//    /**
//     * 通过密码连接到WIFI
//     *
//     * @param scanResult 要连接的WIFI
//     * @param pwd        密码
//     * @param listener   连接的监听
//     */
//    public void connectionWifiByPassword(@NonNull ScanResult scanResult, @Nullable String pwd, @NonNull OnWifiConnectListener listener) {
//        // SSID
//        String SSID = scanResult.SSID;
//        // 加密方式
//        SecurityMode securityMode = getSecurityMode(scanResult);
//
//        // 生成配置文件
//        WifiConfiguration addConfig = createWifiConfiguration(SSID, pwd, securityMode);
//        int netId;
//        // 判断当前配置是否存在
//        WifiConfiguration updateConfig = isExists(addConfig);
//        if (null != updateConfig) {
//            // 更新配置
//            netId = mWifiManager.updateNetwork(updateConfig);
//        } else {
//            // 添加配置
//            netId = mWifiManager.addNetwork(addConfig);
//        }
//        // 通过NetworkID连接到WIFI
//        connectionWifiByNetworkId(SSID, netId, listener);
//    }
//
//
//    /**
//     * 这个枚举用于表示网络加密模式
//     */
//    public enum SecurityMode {
//        OPEN, WEP, WPA, WPA2
//    }
//
//
//    /**
//     * 获取WIFI的加密方式
//     *
//     * @param scanResult WIFI信息
//     * @return 加密方式
//     */
//    public SecurityMode getSecurityMode(@NonNull ScanResult scanResult) {
//        String capabilities = scanResult.capabilities;
//        if (capabilities.contains("WPA")) {
//            return SecurityMode.WPA;
//        } else if (capabilities.contains("WEP")) {
//            return SecurityMode.WEP;
//        } else if (capabilities.contains("WPA2")) {
//            return SecurityMode.WPA2;
//        } else {
//            // 没有加密
//            return SecurityMode.OPEN;
//        }
//    }
//
//
//    /**
//     * 生成新的配置信息 用于连接Wifi
//     *
//     * @param SSID     WIFI名字
//     * @param password WIFI密码
//     * @param mode     WIFI加密类型
//     * @return 配置
//     */
//    private WifiConfiguration createWifiConfiguration(String SSID, String password, SecurityMode mode) {
//        WifiConfiguration config = new WifiConfiguration();
//        config.allowedAuthAlgorithms.clear();
//        config.allowedGroupCiphers.clear();
//        config.allowedKeyManagement.clear();
//        config.allowedPairwiseCiphers.clear();
//        config.allowedProtocols.clear();
//        config.SSID = "\"" + SSID + "\"";
//        if (mode == SecurityMode.OPEN) {
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        } else if (mode == SecurityMode.WEP) {
//            config.hiddenSSID = true;
//            config.wepKeys[0] = "\"" + password + "\"";
//            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            config.wepTxKeyIndex = 0;
//        } else if (mode == SecurityMode.WPA) {
//            config.preSharedKey = "\"" + password + "\"";
//            config.hiddenSSID = true;
//            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//            config.status = WifiConfiguration.Status.ENABLED;
//        }
//        return config;
//    }
//
//
//    /**
//     * 通过NetworkId连接到WIFI （配置过的网络可以直接获取到NetworkID，从而不用再输入密码）
//     *
//     * @param SSID      WIFI名字
//     * @param networkId NetworkId
//     * @param listener  连接的监听
//     */
//    public void connectionWifiByNetworkId(@NonNull String SSID, int networkId, @NonNull OnWifiConnectListener listener) {
//
//        // 连接的回调监听
//        mOnWifiConnectListener = listener;
//        // 连接开始的回调
//        mOnWifiConnectListener.onStart(SSID);
//        /*
//         * 判断 NetworkId 是否有效
//         * -1 表示配置参数不正确，我们获取不到会返回-1.
//         */
//        if (-1 == networkId) {
//            // 连接WIFI失败
//            if (null != mOnWifiConnectListener) {
//                // 配置错误
//                mOnWifiConnectListener.onFailure(SSID);
//                // 连接完成
//                mOnWifiConnectListener.onFinish();
//                mOnWifiConnectListener = null;
//            }
//            return;
//        }
//        // 获取当前的网络连接
//        WifiInfo wifiInfo = getConnectionInfo();
//        if (null != wifiInfo) {
//            // 断开当前连接
//            boolean isDisconnect = disconnectWifi(wifiInfo.getNetworkId());
//            if (!isDisconnect) {
//                // 断开当前网络失败
//                if (null != mOnWifiConnectListener) {
//                    // 断开当前网络失败
//                    mOnWifiConnectListener.onFailure(SSID);
//                    // 连接完成
//                    mOnWifiConnectListener.onFinish();
//                    mOnWifiConnectListener = null;
//                }
//                return;
//            }
//        }
//
//        // 连接WIFI
//        boolean isEnable = mWifiManager.enableNetwork(networkId, true);
//        if (!isEnable) {
//            // 连接失败
//            if (null != mOnWifiConnectListener) {
//                // 连接失败
//                mOnWifiConnectListener.onFailure(SSID);
//                // 连接完成
//                mOnWifiConnectListener.onFinish();
//                mOnWifiConnectListener = null;
//            }
//        }
//    }
//
//
//    /**
//     * @param ssid 可以理解为是wifi的名字
//     * @return 反回的是wifi配置对象
//     */
//    public WifiConfiguration getConfBySSID(String ssid) {
//
//        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();
//        for (WifiConfiguration existingConfig : configuredNetworks) {
//            Log.i(TAG, "系统保存配置的 SSID : " + ssid);
//            if (existingConfig.SSID.equals(ssid)) {
//                return existingConfig;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 获取当前正在连接的WIFI信息
//     *
//     * @return 当前正在连接的WIFI信息
//     */
//    public WifiInfo getConnectionInfo() {
//        try {
//            return mWifiManager.getConnectionInfo();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    /**
//     * 断开WIFI
//     *
//     * @param netId netId
//     * @return 是否断开
//     */
//    public boolean disconnectWifi(int netId) {
//        boolean isDisable = mWifiManager.disableNetwork(netId);
//        boolean isDisconnect = mWifiManager.disconnect();
//        return isDisable && isDisconnect;
//    }
//
//
//    /**
//     * 关闭Wifi
//     */
//    public void closeWifi() {
//        if (mWifiManager.isWifiEnabled()) {
//            mWifiManager.setWifiEnabled(false);
//        }
//    }
//
//
//    public void forget(int netId, InterfaceProxy listener) {
//
//
//        //有部分码友说这使用删除的方式有时候会出现问题，后来去翻看了一下android设置里的源码。
//        //是需是直接掉调用forget方法来删除指写的wifi配置的
//
//        //但是这个方法是藏起来的方法，我们可以看到前面会有一个@hide方法
//
//        /**
//         * Delete the network in the supplicant config.
//         *
//         * This function is used instead of a sequence of removeNetwork()
//         * and saveConfiguration().
//         *
//         * @param config the set of variables that describe the configuration,
//         *            contained in a {@link WifiConfiguration} object.
//         * @param listener for callbacks on success or failure. Can be null.
//         * @throws IllegalStateException if the WifiManager instance needs to be
//         * initialized again
//         * @hide
//         */
////        public void forget(int netId, ActionListener listener) {
////            if (netId < 0) throw new IllegalArgumentException("Network id cannot be negative");
////            validateChannel();
////            sAsyncChannel.sendMessage(FORGET_NETWORK, netId, putListener(listener));
////        }
//
//
//        //那问题又来了，那个ActionListener前面又有一个@hide
//        //哈哈哈！
//
//        /**
//         * Interface for callback invocation on an application action
//         * @hide
//         */
////        public interface ActionListener {
//        /** The operation succeeded */
////            public void onSuccess();
//        /**
//         * The operation failed
//         * @param reason The reason for failure could be one of
//         * {@link #ERROR}, {@link #IN_PROGRESS} or {@link #BUSY}
//         */
////            public void onFailure(int reason);
////        }
//
//
//        //所以这里面我们就要这样子做了
//        //这部分代码是通过反射的方法去获取到ActionListener这个类的字节码对象
//        Class<?> actionListenerClazz = null;
//        try {
//            actionListenerClazz = Class.forName("android.net.wifi.WifiManager$ActionListener");
//            Log.d(TAG, "name == " + actionListenerClazz.getName());
//            Method[] declaredMethods = actionListenerClazz.getDeclaredMethods();
//            Log.d(TAG, "method Size == " + declaredMethods.length);
//            //这些输出，只是为了验证我们拿到的是对的哦！
//            for (int i = 0; i < declaredMethods.length; i++) {
//                Log.d(TAG, "mohtod Name == " + declaredMethods[i].getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        if (actionListenerClazz == null) {
//            throw new RuntimeException("fail to get ActionListener...");
//        }
//
//        Class<? extends WifiManager> wifiClazz = mWifiManager.getClass();
//        try {
//            Method forget = wifiClazz.getDeclaredMethod("forget", int.class, actionListenerClazz);
//            Log.d(TAG, "method name == " + forget);
//            //执行方法
//            forget.invoke(mWifiManager, new Object[]{netId, listener});
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, "e == " + e);
//        }
//        //如果是直接放在系统中编译的话，那么直接是调用wifiManager里的forget方法即可
//    }
//
//
//}
