package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Getter
public enum QuarterCode implements CodeType {
    FIRST("1쿼터", "01"),
    SECOND("2쿼터", "02"),
    THIRD("3쿼터", "03"),
    FOURTH("4쿼터", "04");

    private final String name;
    private final String code;

    QuarterCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**--------------------------------------
     * code값으로 code이름 가져오기
     *---------------------------------------*/
    public static String nameOf( String code ) {
        return CodeTypeUtil.getCodeName( values(), code );
    }

    /**---------------------------------------------
     * 코드 도메인에 속하는 값인지 확인하는 메소드
     * - 코드 도메인에 해당하지 않으면 Exception Throw
     *----------------------------------------------*/
    public static void checkDomain( String code ) {
        if ( !StringUtils.hasText( nameOf( code ) ) ) {
            throw new CustomException( SystemErrorType.INVALID_CODE_DOMAIN_FOR_QUARTER_CODE );
        }
    }

    public static QuarterCode getType( String code ) {
        // TODO 에러 메세지 재정의 필요 쿼터 코드 조회 오류 입니다.
        return Arrays
                .stream( values() )
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElseThrow( () -> new CustomException( SystemErrorType.NOT_FOUND_VALID_VALUE ) );
    }
}
