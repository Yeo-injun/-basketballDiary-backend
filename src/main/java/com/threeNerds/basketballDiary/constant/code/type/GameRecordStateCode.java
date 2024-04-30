package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameRecordStateCode implements CodeType {
    CREATION("게임생성", "01"),
    JOIN_TEAM_CONFIRMATION("참가팀확정", "02"),
    CONFIRMATION("게임확정", "03");

    private final String name;
    private final String code;

    GameRecordStateCode(String name, String code) {
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
    public static GameRecordStateCode typeOf( String code ) {
        return CodeTypeUtil.getEnumType( values(), code );
    }
}
