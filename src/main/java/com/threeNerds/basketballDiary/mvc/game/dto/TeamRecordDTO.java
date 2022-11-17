package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class TeamRecordDTO {
    // 게임Seq
    private String gameSeq;
    // 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
    private String quarterCode;
    // 쿼터시간(경기시간)
    private String quarterTime;

    // 홈_팀Seq
    private Long homeTeamSeq;
    // 홈_팀명
    private String homeTeamName;
    // 홈_총점수
    private Integer homeTotalScore;
    // 홈_팀파울
    private Integer homeFoul;

    // 어웨이_팀Seq
    private Long awayTeamSeq;
    // 어웨이_팀명
    private String awayTeamName;
    // 어웨이_총점수
    private Integer awayTotalScore;
    // 어웨이_팀파울
    private Integer awayFoul;
}
