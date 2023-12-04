package com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request;

import lombok.Getter;

@Getter
public class GetTeamMembersRequest {

    private Long teamSeq;
    private Integer pageNo;

    public GetTeamMembersRequest(Long teamSeq, Integer pageNo) {
        this.teamSeq    = teamSeq;
        this.pageNo     = pageNo;
    }
}
