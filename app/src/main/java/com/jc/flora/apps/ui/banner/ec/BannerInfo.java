package com.jc.flora.apps.ui.banner.ec;

import com.google.gson.annotations.SerializedName;

/**
 * 返回Banner数据
 * http://apiv4.yangkeduo.com/subjects?pdduid=
 * Created by shijincheng on 2017/8/22.
 */
public class BannerInfo {

    public String subject;
    @SerializedName("home_banner_2")
    public String imgUrl;

}
