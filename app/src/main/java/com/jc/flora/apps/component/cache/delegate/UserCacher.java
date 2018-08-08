package com.jc.flora.apps.component.cache.delegate;

/**
 * Created by Shijincheng on 2018/8/8.
 */

public class UserCacher {

    /** 缓存变量保存用的key名 */
    private static final String KEY_USER_NAME = "user_name";
    /** 缓存变量：用户名 */
    private static String sUserName;

    /**
     * 读出时调用
     * @return
     */
    public static String readUserName() {
        if (sUserName == null && Cacher.getSp() != null) {
            sUserName = Cacher.getSp().getString(KEY_USER_NAME, null);
        }
        return sUserName;
    }

    /**
     * 写入时调用
     * @param userName
     */
    public static void writeUserName(String userName) {
        sUserName = userName;
        if (Cacher.edit() != null) {
            Cacher.edit().putString(KEY_USER_NAME, userName);
            Cacher.edit().apply();
        }
    }

    /**
     * 清空以及在应用退出时调用
     */
    public static void clearUserName(){
        sUserName = null;
        Cacher.clear();
    }

}
