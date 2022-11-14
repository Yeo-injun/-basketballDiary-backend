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

    // 게임 총점수
    private Integer gameTotalScore;

    // 쿼터 점수
    private List<QuarterRecordDTO> quarters;

    public GameJoinTeamRecordDTO gameTotalScore(Integer gameTotalScore) {
        this.gameTotalScore = gameTotalScore;
        return this;
    }

    public GameJoinTeamRecordDTO quarters(List<QuarterRecordDTO> quarters) {
        this.quarters = quarters;
        return this;
    }
}
