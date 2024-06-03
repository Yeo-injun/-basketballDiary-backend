package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameTypeCode implements CodeType {
    SELF_GAME("자체전", "01"),
    MATCH_UP_GAME("교류전", "02"),
    COMPETITION("대회", "03");

    private final String name;
    private final String code;

    GameTypeCode(String name, String code) {
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
    public static GameTypeCode typeOf( String code ) {
        return CodeTypeUtil.getEnumType( values(), code );
    }

}
