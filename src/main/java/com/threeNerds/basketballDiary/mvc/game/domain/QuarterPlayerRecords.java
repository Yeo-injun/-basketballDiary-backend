package com.threeNerds.basketballDiary.mvc.game.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuarterPlayerRecords {

    @Builder
    QuarterPlayerRecords(QuarterPlayerRecords qpr) {
        quarterPlayerRecordsSeq = qpr.quarterPlayerRecordsSeq;
        gameSeq = qpr.gameSeq;
        gameJoinPlayerSeq = qpr.gameJoinPlayerSeq;
        gameJoinTeamSeq = qpr.gameJoinTeamSeq;
        quarterCode = qpr.quarterCode;
        inGameYn = qpr.inGameYn;
        tryFreeThrow = qpr.tryFreeThrow;
        freeThrow = qpr.freeThrow;
        tryTwoPoint = qpr.tryTwoPoint;
        twoPoint = qpr.twoPoint;
        tryThreePoint = qpr.tryThreePoint;
        threePoint = qpr.threePoint;
        assist = qpr.assist;
        rebound = qpr.rebound;
        steal = qpr.steal;
        block = qpr.block;
        turnover = qpr.turnover;
        foul = qpr.foul;
    }

    private Long quarterPlayerRecordsSeq;
    private Long gameSeq;
    private Long gameJoinPlayerSeq;
    private Long gameJoinTeamSeq;
    private String quarterCode;
    private String inGameYn;
    private Integer tryFreeThrow;
    private Integer freeThrow;
    private Integer tryTwoPoint;
    private Integer twoPoint;
    private Integer tryThreePoint;
    private Integer threePoint;
    private Integer assist;
    private Integer rebound;
    private Integer steal;
    private Integer block;
    private Integer turnover;
    private Integer foul;

    public Integer getTotalScore() {
        return freeThrow + twoPoint * 2 + threePoint * 3;
    }
}
