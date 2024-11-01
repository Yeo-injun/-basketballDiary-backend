package com.threeNerds.basketballDiary.auth.validation.game;

import com.threeNerds.basketballDiary.auth.AuthType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameAuth implements AuthType {
    /*---------------------------
     * 권한유형 : 경기기록 권한
     * cf. 권한수준이 낮을수록 더 많은 권한을 가짐.
     *---------------------------*/
    NONE(               "기록자아님" , "99" ),
    GAME_CREATOR(       "경기생성자" , "01" ),
    GAME_RECORDER(      "경기기록자" , "02" )
    ;

    private final String name;
    private final String level;

    GameAuth( String name, String level ) {
        this.name   = name;
        this.level  = level;
    }

    @Override
    public boolean isPermissionGranted( AuthType userAuth ) {
        int userAuthLevel       = Integer.parseInt( userAuth.getLevel() );
        int requiredAuthLevel   = Integer.parseInt( this.level );
        return requiredAuthLevel >=  userAuthLevel;
    }

    // 권한유형에 맞는 권한수준을 찾아서 해당되는 권한 유형으로 리턴
    public static GameAuth ofLevel( String authLevel ) {
        if ( null == authLevel ) {
            return GameAuth.NONE;
        }
        return Arrays.stream( values() )
                .filter( item -> item.getLevel().equals( authLevel )  )
                .findAny()
                .orElseGet( () -> GameAuth.NONE );
    }

}
