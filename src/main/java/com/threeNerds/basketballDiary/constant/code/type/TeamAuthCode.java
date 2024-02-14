package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.UserAuthConst;
import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TeamAuthCode implements CodeType {

    // TODO 상수의 코드값 부여 정책 정해야 함 - 권한처리시 사용해야 하니까.
    // TODO 22.02.26(인준) code의 자료형을 int로 수정하는 것은? long타입은 과할것 같음
    LEADER("팀장", Long.toString(UserAuthConst.LEADER)),
    MANAGER("관리자", Long.toString(UserAuthConst.MANAGER)),
    TEAM_MEMBER("일반팀원", Long.toString(UserAuthConst.TEAM_MEMBER)),
    USER("사용자", Long.toString(UserAuthConst.USER));

    private final String name;
    private final String code;

    TeamAuthCode(String name, String code) {
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
