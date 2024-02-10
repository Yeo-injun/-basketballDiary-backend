package com.threeNerds.basketballDiary.constant.code;

import java.util.Arrays;

public class CodeTypeUtil {

    /* CodeType 타입을 구현한 Enum클래스 배열에서 code값의 name을 찾는 메소드 */
    public static String getCodeName( CodeType[] codeTypes, String code ) {
        return Arrays.stream( codeTypes )
                .filter( item -> item.getCode().equals( code ) )
                .map( CodeType::getName )
                .findAny()
                .orElse("");
    }
}
