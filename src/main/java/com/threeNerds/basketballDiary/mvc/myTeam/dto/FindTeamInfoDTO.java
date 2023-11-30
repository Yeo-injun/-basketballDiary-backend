package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class FindTeamInfoDTO {
    private Long userSeq;
    private Long teamSeq;

    public FindTeamInfoDTO ( Long teamSeq, Long userSeq ) {
        this.teamSeq = teamSeq;
        this.userSeq = userSeq;
    }
}