package com.jc.flora.apps.component.request.volley;

import com.jc.flora.apps.component.request.NetResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijincheng on 2018/2/10.
 */

public class BaseResponse {

    public int count;
    public boolean error;

    public static class ArticleList extends BaseResponse{
        public List<NetResponse.Result> results = new ArrayList<>();
    }

}
