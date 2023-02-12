package com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request;

import lombok.Getter;

@Getter
public class GetMemeberGradeRequest {

    private Long teamSeq;
    private Integer pageNo;

    public GetMemeberGradeRequest(Long teamSeq, Integer pageNo ) {
        this.teamSeq = teamSeq;
        this.pageNo = pageNo;
    }
}
