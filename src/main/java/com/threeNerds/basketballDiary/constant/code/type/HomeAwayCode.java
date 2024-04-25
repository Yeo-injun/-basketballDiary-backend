package com.threeNerds.basketballDiary.constant.code.type;


import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public enum HomeAwayCode implements CodeType {
    HOME_TEAM("홈팀", "01"),
    AWAY_TEAM("어웨이팀", "02");

    private final String name;
    private final String code;

    HomeAwayCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**--------------------------------------
     * code값으로 code이름 가져오기
     *---------------------------------------*/
    public static String nameOf( String code ) {
        return CodeTypeUtil.getCodeName( values(), code );
    }

    /**--------------------------------------
     * code값으로 enum객체 가져오기
     *---------------------------------------*/
    public static HomeAwayCode typeOf( String code ) {
        return CodeTypeUtil.getEnumType( values(), code );
    }

    /**---------------------------------------------
     * 코드 도메인에 속하는 값인지 확인하는 메소드
     * - 코드 도메인에 해당하지 않으면 Exception Throw
     *----------------------------------------------*/
    public static void checkDomain( String code ) {
        if ( !StringUtils.hasText( nameOf( code ) ) ) {
            throw new CustomException( SystemErrorType.INVALID_CODE_DOMAIN_FOR_HOME_AWAY_CODE );
        }
    }
}
