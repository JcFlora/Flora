package com.jc.flora.apps.component.request;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回json实体类示例
 * https://gank.io/api/search/query/listview/category/Android/count/2/page/1
 * Created by shijincheng on 2017/1/13.
 */
public class NetResponse extends BaseResponse{

    public List<Result> results = new ArrayList<>();

    public NetResponse(){}

    public NetResponse(JSONObject json) throws JSONException {
        count = json.optInt("count");
        error = json.optBoolean("error");
        optResultsFromJson(json, "results");
    }

    private void optResultsFromJson(JSONObject json, String resultsKey)throws JSONException {
        JSONArray array = json.optJSONArray(resultsKey);
        for (int i = 0, length = array.length(); i < length; i++) {
            JSONObject jsonResult = (JSONObject)array.get(i);
            Result result = new Result(jsonResult);
            results.add(result);
        }
    }

    public static class Result{
        public String desc;
        @SerializedName("ganhuo_id")
        public String ganhuoId;
        public String publishedAt;
        public String type;
        public String url;
        public String who;

        public Result(){}

        public Result(JSONObject json){
            desc = json.optString("desc");
            ganhuoId = json.optString("ganhuo_id");
            publishedAt = json.optString("publishedAt");
            type = json.optString("type");
            url = json.optString("url");
            who = json.optString("who");
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("desc:"+desc+"\n");
            builder.append("ganhuoId:"+ganhuoId+"\n");
            builder.append("publishedAt:"+publishedAt+"\n");
            builder.append("type:"+type+"\n");
            builder.append("url:"+url+"\n");
            builder.append("who:"+who);
            return builder.toString();
        }
    }

}
