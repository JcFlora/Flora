package com.jc.flora.apps.component.audio.delegate;

/**
 * 播放模式（循环、单曲、随机）
 * Created by Samurai on 2017/10/9.
 */
public enum AudioPlayMode {

    LOOP(0), SINGLE(1), SHUFFLE(2);

    private int mValue;

    AudioPlayMode(int value) {
        mValue = value;
    }

    public static AudioPlayMode valueOf(int value){
        switch (value) {
            case 1:
                return SINGLE;
            case 2:
                return SHUFFLE;
            case 0:
            default:
                return LOOP;
        }
    }

    public int value(){
        return mValue;
    }

}