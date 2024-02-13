package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlayerTypeCode implements CodeType {

    TEAM_MEMBER("팀원", "01"),
    AUTH_GUEST("게스트(회원)", "02"),
    UNAUTH_GUEST("게스트(비회원)", "03");

    private final String name;
    private final String code;

    PlayerTypeCode(String name, String code) {
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
