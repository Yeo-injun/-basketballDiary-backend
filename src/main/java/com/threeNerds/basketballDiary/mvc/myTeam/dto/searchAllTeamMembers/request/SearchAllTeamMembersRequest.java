package com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.request;

import lombok.Getter;

@Getter
public class SearchAllTeamMembersRequest {

    private Long teamSeq;
    private Integer pageNo;
    private String playerName;

    public SearchAllTeamMembersRequest(Long teamSeq, Integer pageNo, String playerName ) {
        this.teamSeq = teamSeq;
        this.pageNo = pageNo;
        this.playerName = playerName;
    }
}
