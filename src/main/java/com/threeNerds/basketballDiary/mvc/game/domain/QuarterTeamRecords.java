package com.threeNerds.basketballDiary.mvc.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuarterTeamRecords {

    final private int SCORE_ONE = 1;
    final private int SCORE_TWO = 2;
    final private int SCORE_THREE = 3;

    private Long quarterTeamRecordsSeq; // 쿼터팀기록Seq
    private Long gameSeq;           // 게임Seq
    private Long gameJoinTeamSeq;   // 게임참가팀Seq

    private String quarterCode;        // 팀명
    private String quarterTime; // 쿼터시간

    private Integer freeThrow;
    private Integer twoPoint;
    private Integer threePoint;
    private Integer rebound;
    private Integer steal;
    private Integer block;
    private Integer turnOver;
    private Integer foul;

    public Integer getQuarterTotalScore()
    {
        int freeThrowScore = this.freeThrow.intValue() * SCORE_ONE;
        int twoPointScore = this.twoPoint.intValue() * SCORE_TWO;
        int threePointScore = this.threePoint.intValue() * SCORE_THREE;

        Integer totalScore = freeThrowScore + twoPointScore + threePointScore;
        return totalScore;
    }

}
