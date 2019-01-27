package com.jc.flora.apps.component.cache.delegate;

/**
 * Created by Shijincheng on 2018/8/8.
 */

public class UserCacher2 {

    /** 缓存变量保存用的key名 */
    private static final String KEY_USER = "user";
    /** 缓存变量：用户 */
    private static User sUser;

    /**
     * 读出时调用
     * @return
     */
    public static User readUser() {
        if (sUser == null && Cacher.getSp() != null) {
            sUser = Cacher.stringToObject(Cacher.getSp().getString(KEY_USER, null), User.class);
        }
        return sUser;
    }

    /**
     * 写入时调用
     * @param user
     */
    public static void writeUser(User user) {
        sUser = user;
        if (Cacher.edit() != null) {
            Cacher.edit().putString(KEY_USER, Cacher.objectToString(user));
            Cacher.edit().apply();
        }
    }

    /**
     * 清空以及在应用退出时调用
     */
    public static void clearUser(){
        sUser = null;
        if (Cacher.edit() != null) {
            Cacher.edit().remove(KEY_USER);
            Cacher.edit().apply();
        }
    }

}
