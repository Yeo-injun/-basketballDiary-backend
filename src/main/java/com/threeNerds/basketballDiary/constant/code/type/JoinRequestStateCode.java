package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum JoinRequestStateCode implements CodeType {

    WAITING("대기중", "01"),
    APPROVAL("승인", "02"),
    REJECTION("거절", "03"),
    CANCEL("취소", "04");

    private final String name;
    private final String code;

    JoinRequestStateCode(String name, String code) {
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
    public static JoinRequestStateCode ofType( String code ) {
        return CodeTypeUtil.getEnumType( values(), code );
    }

}
