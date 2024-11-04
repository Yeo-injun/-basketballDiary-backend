package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.auth.validation.type.GameAuth;
import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

@Getter
public enum GameRecordAuthCode implements CodeType {
    CREATOR(    "게임생성자" , GameAuth.GAME_CREATOR.getLevel() ),
    RECORDER(   "입력권한자" , GameAuth.GAME_RECORDER.getLevel() );

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
