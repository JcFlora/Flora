package com.jc.flora.apps.component.video.player;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public class PlayerFactory {

    public static BasePlayer get(PlayerType type){
        switch (type){
            case MEDIA:
                return new SysMediaPlayer();
            case EXO:
                return new ExoMediaPlayer();
            case IJK:
            default:
                return new IjkPlayer();
        }
    }

    public enum PlayerType{
        MEDIA, EXO, IJK
    }

}