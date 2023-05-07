package com.threeNerds.basketballDiary.mvc.user.dto;

import lombok.Getter;

@Getter
public class UserInqCondDTO {
    /** 팀Seq **/
    private Long teamSeq;
    /** 사용자명 **/
    private String userName;
    /** 이메일 **/
    private String email;

    public UserInqCondDTO teamSeq( Long teamSeq ) {
        this.teamSeq = teamSeq;
        return this;
    }

    public UserInqCondDTO userName( String userName ) {
        this.userName = userName;
        return this;
    }

    public UserInqCondDTO email( String email ) {
        this.email = email;
        return this;
    }
}
