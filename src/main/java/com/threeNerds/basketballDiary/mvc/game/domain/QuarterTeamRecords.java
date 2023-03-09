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
    private Long gameSeq;               // 게임Seq
    private Long gameJoinTeamSeq;       // 게임참가팀Seq

    private String quarterCode;         // 쿼터코드
    private String quarterTime;         // 쿼터시간

    private int score;
    private int freeThrow;
    private int twoPoint;
    private int threePoint;
    private int assist;
    private int rebound;
    private int steal;
    private int block;
    private int turnover;
    private int foul;

    /** 쿼터팀레코드 초기화 */
    public QuarterTeamRecords( Long gameSeq, Long gameJoinTeamSeq, String quarterCode ) {
        this.gameSeq = gameSeq;
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        this.quarterCode = quarterCode;
        this.quarterTime = "0000";
    }

    public void calculateQuarterTotalScore()
    {
        int freeThrowScore  = this.freeThrow * SCORE_ONE;
        int twoPointScore   = this.twoPoint * SCORE_TWO;
        int threePointScore = this.threePoint * SCORE_THREE;
        
        // 현재 쿼터의 총 득점 계산
        this.score = freeThrowScore + twoPointScore + threePointScore;
    }

}
