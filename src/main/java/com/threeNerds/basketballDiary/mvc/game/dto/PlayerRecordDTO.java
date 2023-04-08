package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerRecordDTO {

    /** GAME_JOIN_TEAM */
    private String gameSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;

    /** GAME_JOIN_PLAYER */
    private Long gameJoinPlayerSeq;
    private String playerTypeCode;
    private String playerTypeCodeName;
    private Long userSeq;
    private String name;
    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    /** QUARTER_PLAYER_RECORDS */
    /** 쿼터별선수기록 SEQ */
    private Long quarterPlayerRecordsSeq;
    /** 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반) */
    private String quarterCode;
    private int freeThrow;
    private int tryFreeThrow;
    private int twoPoint;
    private int tryTwoPoint;
    private int threePoint;
    private int tryThreePoint;
    private int totalScore;
    private int assist;
    private int rebound;
    private int steal;
    private int block;
    private int turnover;
    private int foul;

    public PlayerRecordDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public PlayerRecordDTO gameSeq(String gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }


    public PlayerRecordDTO playerTypeCode(String playerTypeCode) {
        this.playerTypeCode = playerTypeCode;
        return this;
    }

    public PlayerRecordDTO playerTypeCodeName(String playerTypeCodeName) {
        this.playerTypeCodeName = playerTypeCodeName;
        return this;
    }

    public PlayerRecordDTO gameJoinPlayerSeq(Long gameJoinPlayerSeq) {
        this.gameJoinPlayerSeq = gameJoinPlayerSeq;
        return this;
    }

    public PlayerRecordDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }
    public PlayerRecordDTO name(String name) {
        this.name = name;
        return this;
    }
    public PlayerRecordDTO backNumber(String backNumber) {
        this.backNumber = backNumber;
        return this;
    }
    public PlayerRecordDTO positionCode(String positionCode) {
        this.positionCode = positionCode;
        return this;
    }

    public PlayerRecordDTO quarterPlayerRecordsSeq(Long quarterPlayerRecordsSeq) {
        this.quarterPlayerRecordsSeq = quarterPlayerRecordsSeq;
        return this;
    }
    public PlayerRecordDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }



    public PlayerRecordDTO freeThrow(int freeThrow) {
        this.freeThrow = freeThrow;
        return this;
    }
    public PlayerRecordDTO tryFreeThrow(int tryFreeThrow) {
        this.tryFreeThrow = tryFreeThrow;
        return this;
    }
    public PlayerRecordDTO twoPoint(int twoPoint) {
        this.twoPoint = twoPoint;
        return this;
    }
    public PlayerRecordDTO tryTwoPoint(int tryTwoPoint) {
        this.tryTwoPoint = tryTwoPoint;
        return this;
    }
    public PlayerRecordDTO threePoint(int threePoint) {
        this.threePoint = threePoint;
        return this;
    }
    public PlayerRecordDTO tryThreePoint(int tryThreePoint) {
        this.tryThreePoint = tryThreePoint;
        return this;
    }
    public PlayerRecordDTO totalScore(int totalScore) {
        this.totalScore = totalScore;
        return this;
    }
    public PlayerRecordDTO assist(int assist) {
        this.assist = assist;
        return this;
    }
    public PlayerRecordDTO rebound(int rebound) {
        this.rebound = rebound;
        return this;
    }
    public PlayerRecordDTO steal(int steal) {
        this.steal = steal;
        return this;
    }
    public PlayerRecordDTO block(int block) {
        this.block = block;
        return this;
    }
    public PlayerRecordDTO turnover(int turnover) {
        this.turnover = turnover;
        return this;
    }
    public PlayerRecordDTO foul(int foul) {
        this.foul = foul;
        return this;
    }
}
