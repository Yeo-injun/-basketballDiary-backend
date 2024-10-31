package com.threeNerds.basketballDiary.auth.validation.team;

import com.threeNerds.basketballDiary.auth.AuthType;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TeamAuth implements AuthType {
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
    NONE( "팀원아님", -1 ),
    TEAM_MEMBER( "팀원"      , 1    ),
    TEAM_MANAGER(  "팀관리자" , 2   ),      // 모든 권한 유형보다 높은 수준을 가짐
    TEAM_LEADER(   "팀장"   , 3   )      // 시스템내 최고 권한 수준을 가짐
    ;
    private final String type;
    private final String name;
    private final int level;

    TeamAuth( String name, int level ) {
        this.type   = "team";
        this.name   = name;
        this.level  = level;
    }

    public boolean isPermissionGranted( AuthType userAuth ) {
        if ( null == userAuth ) {
            return false;
        }
        return userAuth.getLevel() >= this.level;
    }
    
    // 권한수준에 따른 TeamAuth객체 리턴
    public static TeamAuth of( Integer authLevel ) {
        if ( null == authLevel ) {
            return TeamAuth.NONE;
        }
        return Arrays.stream( values() )
                .filter( item -> item.getLevel() == authLevel )
                .findAny()
                .orElseGet( () -> TeamAuth.NONE );
    }

}
