package com.threeNerds.basketballDiary.auth.constant;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthLevel {
    /**
     * 특정권한유형을 갖게될 경우 해당 권한 유형의 level은 1부터 시작한다.
     * level이 높아질수록 더 많은 권한을 가져간다.
     * 회원인 경우 제일 낮은 level 0의 권한을 갖는다.
     */
    /*---------------------------
     * 권한유형 : 일반사용자 권한
     * - MEMBER : 일반회원
     * - ADMIN : 관리자
     * - ROOT : 슈퍼 관리자
     *---------------------------*/
    MEMBER(         "user", "일반회원"       , 0 ),
    ADMIN(          "user", "관리자"        , 90),      // 모든 권한 유형보다 높은 수준을 가짐
    ROOT(           "user", "최상위관리자"   , 91),      // 시스템내 최고 권한 수준을 가짐
    
    /*---------------------------
     * 권한유형 : 소속팀 권한
     *---------------------------*/
    TEAM_MEMBER(    "team", "팀원"     , 1 ),
    TEAM_MANAGER(   "team", "팀관리자"  , 2 ),
    TEAM_LEADER(    "team", "팀장"     , 3 ),

    /*---------------------------
     * 권한유형 : 경기기록 권한
     * cf. 권한수준이 낮을수록 더 많은 권한을 가짐.
     *---------------------------*/
    GAME_CREATOR(    "gameRecorder", "경기생성자"  , 1 ),
    GAME_RECORDER(   "gameRecorder", "경기기록자"  , 2 );

    private final String type;
    private final String name;
    private final int level;

    AuthLevel( String authType, String name, int level ) {
        this.type = authType;
        this.name = name;
        this.level = level;
    }

    // 권한유형에 맞는 권한수준을 찾아서 AuthLevel 타입으로 리턴
    public static AuthLevel of( String authType, int authLevel ) {
        return Arrays.stream( values() )
                .filter( item -> item.getType().equals( authType ) && item.getLevel() == authLevel )
                .findAny()
                .orElseThrow( () -> new CustomException( SystemErrorType.NOT_FOUND_VALID_AUTH_INFO ) );
    }

}
