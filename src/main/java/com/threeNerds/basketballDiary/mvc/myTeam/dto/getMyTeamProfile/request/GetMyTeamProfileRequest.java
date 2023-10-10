package com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request;

import lombok.Getter;

@Getter
public class GetMyTeamProfileRequest {

    private Long userSeq;
    private Long teamSeq;

    public GetMyTeamProfileRequest( Long userSeq, Long teamSeq ) {
        this.userSeq = userSeq;
        this.teamSeq = teamSeq;
    }
}
