package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GameJoinTeamRecordDTO {

    private Long gameJoinTeamSeq;
    private Long teamSeq;

    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;

    // 쿼터 점수
    private List<QuarterRecordDTO> quarters;
}
