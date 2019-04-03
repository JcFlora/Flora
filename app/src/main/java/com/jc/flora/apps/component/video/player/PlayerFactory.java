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
                return new SysMediaPlayer();
            case IJK:
            default:
                return new SysMediaPlayer();
        }
    }

    public enum PlayerType{
        MEDIA, EXO, IJK
    }

}