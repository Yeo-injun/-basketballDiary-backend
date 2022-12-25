package com.threeNerds.basketballDiary.mvc.team.dto;

import lombok.Getter;

@Getter
public class PlayerSearchDTO {
    // 팀SEQ
    private Long teamSeq;
    // 가입요청상태코드
    private String joinRequestStateCode;
    // 가입요청유형코드
    private String joinRequestTypeCode;

    public PlayerSearchDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public PlayerSearchDTO joinRequestStateCode (String joinRequestStateCode) {
        this.joinRequestStateCode = joinRequestStateCode;
        return this;
    }

    public PlayerSearchDTO joinRequestTypeCode (String joinRequestTypeCode) {
        this.joinRequestTypeCode = joinRequestTypeCode;
        return this;
    }

}
