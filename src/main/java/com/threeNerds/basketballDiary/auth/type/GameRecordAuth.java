package com.threeNerds.basketballDiary.auth.type;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameRecordAuth implements AuthType {
    /*---------------------------
     * 권한유형 : 경기기록 권한
     * cf. 권한수준이 낮을수록 더 많은 권한을 가짐.
     *---------------------------*/
    GAME_CREATOR(    "경기생성자"  , 1 ),
    GAME_RECORDER(   "경기기록자"  , 2 );

    private final String type;
    private final String name;
    private final int level;

    GameRecordAuth( String name, int level ) {
        this.type = "gameRecorder";
        this.name = name;
        this.level = level;
    }

    // 권한유형에 맞는 권한수준을 찾아서 AuthLevel 타입으로 리턴
    public static GameRecordAuth of( int authLevel ) {
        return Arrays.stream( values() )
                .filter( item -> item.getLevel() == authLevel )
                .findAny()
                .orElseThrow( () -> new CustomException( SystemErrorType.NOT_FOUND_VALID_AUTH_INFO ) );
    }

}
