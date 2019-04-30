package com.jc.flora.apps.component.audio.player;

/**
 * Created by Shijincheng on 2019/4/26.
 */

public class AudioPlayerFactory {

    public static BaseAudioPlayer get(PlayerType type){
        switch (type){
            case EXO:
                return new ExoAudioPlayer();
            case IJK:
            default:
                return new IjkAudioPlayer();
        }
    }

    public enum PlayerType{
        EXO, IJK
    }

}