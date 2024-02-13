package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DayOfTheWeekCode implements CodeType {
    MON("월", "1"),
    TUE("화", "2"),
    WED("수", "3"),
    THU("목", "4"),
    FRI("금", "5"),
    SAT("토", "6"),
    SUN("일", "7");

    private final String name;
    private final String code;

    DayOfTheWeekCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**--------------------------------------
     * code값으로 code이름 가져오기
     *---------------------------------------*/
    public static String nameOf( String code ) {
        return CodeTypeUtil.getCodeName( values(), code );
    }

}
