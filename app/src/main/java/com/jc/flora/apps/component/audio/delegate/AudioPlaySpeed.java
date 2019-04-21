package com.jc.flora.apps.component.audio.delegate;

/**
 * 播放速度（0.75, 1.0, 1.2, 1.5, 2.0）
 * Created by Samurai on 2019/4/20.
 */
public enum AudioPlaySpeed {

    X_075(0, 0.75f), X_10(1, 1f), X_12(2, 1.2f), X_15(3, 1.5f), X_20(4, 2.0f);

    private int mIndex;
    private float mValue;

    AudioPlaySpeed(int index, float value) {
        mIndex = index;
        mValue = value;
    }

    public static AudioPlaySpeed speedOf(int index){
        switch (index) {
            case 0:
                return X_075;
            case 2:
                return X_12;
            case 3:
                return X_15;
            case 4:
                return X_20;
            case 1:
            default:
                return X_10;
        }
    }

    public int index(){
        return mIndex;
    }

    public float value(){
        return mValue;
    }

}