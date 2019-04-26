package com.jc.flora.apps.component.audio.delegate;

import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/10/16.
 */

public class AudioSourceInterceptor {


    /**
     * 在播放前进行拦截，返回true表示拦截，false表示不拦截
     * @return 是否拦截，默认不拦截
     */
    public boolean interceptPlay(){
        return false;
    }

    /**
     * 在选择前进行拦截，返回true表示拦截，false表示不拦截
     * @param mp3List
     * @param index
     * @return 是否拦截，默认不拦截
     */
    public boolean interceptSelect(ArrayList<MP3> mp3List, int index){
        return false;
    }

}