package com.threeNerds.basketballDiary.auth.type;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserAuth implements AuthType {
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
    MEMBER( "일반회원"       , 0 ),
    ADMIN(  "관리자"        , 90),      // 모든 권한 유형보다 높은 수준을 가짐
    ROOT(   "최상위관리자"   , 91)      // 시스템내 최고 권한 수준을 가짐
    ;
    private final String type;
    private final String name;
    private final int level;

    UserAuth( String name, int level ) {
        this.type = "user";
        this.name = name;
        this.level = level;
    }

    // 권한유형에 맞는 권한수준을 찾아서 AuthLevel 타입으로 리턴
    public static UserAuth of( int authLevel ) {
        return Arrays.stream( values() )
                .filter( item -> item.getLevel() == authLevel )
                .findAny()
                .orElseThrow( () -> new CustomException( SystemErrorType.NOT_FOUND_VALID_AUTH_INFO ) );
    }

}
