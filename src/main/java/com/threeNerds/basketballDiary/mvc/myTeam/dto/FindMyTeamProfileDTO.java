package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class FindMyTeamProfileDTO {
    private Long userSeq;
    private Long teamSeq;

    public FindMyTeamProfileDTO userSeq(Long userSeq){
        this.userSeq=userSeq;
        return this;
    }
    public FindMyTeamProfileDTO teamSeq(Long teamSeq){
        this.teamSeq=teamSeq;
        return this;
    }
}