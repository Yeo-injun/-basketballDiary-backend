package com.threeNerds.basketballDiary.constant.code;


import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum HomeAwayCode {
    HOME_TEAM("홈팀", "01"),
    AWAY_TEAM("어웨이팀", "02");

    private final String name;
    private final String code;

    HomeAwayCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf( String code ) {
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(HomeAwayCode::getName)
                .findAny()
                .orElse("");
        return codeName;
    }

    /**---------------------------------------------
     * 코드 도메인에 속하는 값인지 확인하는 메소드
     * - 코드 도메인에 해당하지 않으면 Exception Throw
     *----------------------------------------------*/
    public static boolean checkDomain( String code ) {
        if ( "".equals( nameOf( code ) ) ) {
            throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE_DOMAIN );
        }
        return true;
    }
}
