package com.threeNerds.basketballDiary.constant.code;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;

import java.util.Arrays;

public class CodeTypeUtil {

    /* CodeType 타입을 구현한 Enum클래스 배열에서 code값의 name을 찾는 메소드 */
    public static String getCodeName( CodeType[] codeTypes, String code ) {
        // Stream으로 구현한 이유 : https://tecoble.techcourse.co.kr/post/2021-09-30-java8-functional-programming/
        // Steam API정리 : https://tecoble.techcourse.co.kr/post/2021-05-23-stream-api-basic/
        return Arrays.stream( codeTypes )
                .filter( item -> item.getCode().equals( code ) )
                .map( CodeType::getName )
                .findAny()
                .orElse("");
    }


    public static < T extends CodeType > T getEnumType( T[] codeTypes, String code ) {
        return Arrays.stream( codeTypes )
                .filter( item -> item.getCode().equals( code ) )
                .findAny()
                .orElseGet( () -> null );
    }

}
