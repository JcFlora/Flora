package com.jc.flora.apps.component.cache.delegate;

/**
 * Created by Shijincheng on 2018/8/8.
 */

public class UserSpreader {

    /** 缓存变量保存用的key名 */
    private static final String KEY_USER_NAME = "user_name";

    /**
     * 读出时调用
     * @return
     */
    public static String readUserName() {
        return Spreader.getSp().getString(KEY_USER_NAME, null);
    }

    /**
     * 写入时调用
     * @param userName
     */
    public static void writeUserName(String userName) {
        if (Spreader.edit() != null) {
            Spreader.edit().putString(KEY_USER_NAME, userName);
            Spreader.edit().apply();
        }
    }

    /**
     * 清空时调用
     */
    public static void clearUserName(){
        writeUserName(null);
    }

}
