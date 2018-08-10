package com.jc.flora.apps.component.cache.delegate;

/**
 * Created by Shijincheng on 2018/8/9.
 */

public class User {

    /** 用户id */
    public int id;
    /** 用户名 */
    public String name;
    /** 昵称 */
    public String nickName;
    /** 电话 */
    public String phone;
    /** 令牌 */
    public String token;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("用户id：").append(id).append("\n");
        sb.append("用户名：").append(name).append("\n");
        sb.append("昵称：").append(nickName).append("\n");
        sb.append("电话：").append(phone).append("\n");
        sb.append("令牌：").append(token).append("\n");
        return sb.toString();
    }
}