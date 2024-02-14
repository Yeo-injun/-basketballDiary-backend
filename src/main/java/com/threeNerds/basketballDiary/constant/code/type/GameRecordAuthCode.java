package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameRecordAuthCode implements CodeType {
    CREATOR("게임생성자", "01"),
    ONLY_WRITER("입력권한자", "02");

    private final String name;
    private final String code;

    GameRecordAuthCode(String name, String code) {
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
