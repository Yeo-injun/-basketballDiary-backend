package com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagerGrade.request;

import lombok.Getter;

@Getter
public class GetManagerGradeRequest {
    private Long teamSeq;

    public GetManagerGradeRequest(Long teamSeq) {
        this.teamSeq = teamSeq;
    }
}
