package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.auth.validation.team.TeamAuth;
import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;


@Getter
public enum TeamAuthCode implements CodeType {

    LEADER(     "팀장"     , Integer.toString( TeamAuth.TEAM_LEADER.getLevel() ) ),
    MANAGER(    "관리자"    , Integer.toString( TeamAuth.TEAM_MANAGER.getLevel() ) ),
    TEAM_MEMBER("일반팀원"  , Integer.toString( TeamAuth.TEAM_MEMBER.getLevel() ) );

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
