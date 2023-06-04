package com.threeNerds.basketballDiary.mvc.myTeam.dto.getMemberGrade.request;

import lombok.Getter;

@Getter
public class GetMemberGradeRequest {

    private Long teamSeq;

    public GetMemberGradeRequest(Long teamSeq) {
        this.teamSeq = teamSeq;
    }
}