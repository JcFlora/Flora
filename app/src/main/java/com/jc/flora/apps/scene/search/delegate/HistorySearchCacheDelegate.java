package com.jc.flora.apps.scene.search.delegate;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.flora.apps.component.cache.delegate.Spreader;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchCacheDelegate {
    /**
     * 缓存变量保存用的key名
     */
    public static final String HISTORY_SEARCH = "HISTORY_SEARCH";

    public static void clear() {
        write("");
    }

    public static List<String> getHistorySearch() {
        String json = Spreader.getSp().getString(HISTORY_SEARCH, "");
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();

        } else {
            return new Gson().fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        }
    }

    public static void writeHistorySearch(String searchWord) {
        //获取本地 历史搜索的json缓存
        String json = Spreader.getSp().getString(HISTORY_SEARCH, "");
        // json 转 List
        List<String> historySearchList;
        if (TextUtils.isEmpty(json)) {
            historySearchList = new ArrayList<>();
        } else {
            historySearchList = new Gson().fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        }
        //去除重复
        for (int i = 0; i < historySearchList.size(); i++) {
            if (searchWord.equals(historySearchList.get(i))){
                historySearchList.remove(i);
                break;
            }
        }
        //最多存储十个搜索数据
        if (historySearchList.size() >= 10) {
            historySearchList.remove(historySearchList.size() - 1);
        }
        //新增的搜索记录放在第一位
        historySearchList.add(0, searchWord);
        //sp存入本地
        write(new Gson().toJson(historySearchList));
    }

    private static void write(String json) {
        if (Spreader.edit() != null) {
            Spreader.edit().putString(HISTORY_SEARCH, json);
            Spreader.edit().apply();
        }
    }
}
