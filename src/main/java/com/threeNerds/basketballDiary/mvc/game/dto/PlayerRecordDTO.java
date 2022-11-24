package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerRecordDTO {

    /**
     * GAME_JOIN_TEAM
     */
    /** 게임참여팀 SEQ */
    private Long gameJoinTeamSeq;
    private String homeAwayCode;
    private String gameSeq;
    private String teamSeq;

    /**
     * GAME_JOIN_PLAYER
     */
    /** 유저 SEQ */
    private Long gameJoinPlayerSeq;
    private Long userSeq;
    private String name;
    private String backNumber;
    private String positionCode;

    private String userImage;

    /**
     * QUARTER_PLAYER_RECORDS
     */
    /** 쿼터별선수기록 SEQ */
    private Long quarterPlayerRecordsSeq;
    /** 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반) */
    private String quarterCode;
    private Long freeThrow;
    private Long tryFreeThrow;
    private Long twoPoint;
    private Long tryTwoPoint;
    private Long threePoint;
    private Long tryThreePoint;
    private Long totalScore;
    private Long assist;
    private Long rebound;
    private Long steal;
    private Long block;
    private Long turnover;
    private Long foul;


    public PlayerRecordDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public PlayerRecordDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public PlayerRecordDTO gameSeq(String gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public PlayerRecordDTO teamSeq(String teamSeq) {
        this.teamSeq = teamSeq;
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
    public PlayerRecordDTO userImage(String userImage) {
        this.userImage = userImage;
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

    public PlayerRecordDTO freeThrow(Long freeThrow) {
        this.freeThrow = freeThrow;
        return this;
    }
    public PlayerRecordDTO tryFreeThrow(Long tryFreeThrow) {
        this.tryFreeThrow = tryFreeThrow;
        return this;
    }
    public PlayerRecordDTO twoPoint(Long twoPoint) {
        this.twoPoint = twoPoint;
        return this;
    }
    public PlayerRecordDTO tryTwoPoint(Long tryTwoPoint) {
        this.tryTwoPoint = tryTwoPoint;
        return this;
    }
    public PlayerRecordDTO threePoint(Long threePoint) {
        this.threePoint = threePoint;
        return this;
    }
    public PlayerRecordDTO tryThreePoint(Long tryThreePoint) {
        this.tryThreePoint = tryThreePoint;
        return this;
    }
    public PlayerRecordDTO totalScore(Long totalScore) {
        this.totalScore = totalScore;
        return this;
    }
    public PlayerRecordDTO assist(Long assist) {
        this.assist = assist;
        return this;
    }
    public PlayerRecordDTO rebound(Long rebound) {
        this.rebound = rebound;
        return this;
    }
    public PlayerRecordDTO steal(Long steal) {
        this.steal = steal;
        return this;
    }
    public PlayerRecordDTO block(Long block) {
        this.block = block;
        return this;
    }
    public PlayerRecordDTO turnover(Long turnover) {
        this.turnover = turnover;
        return this;
    }
    public PlayerRecordDTO foul(Long foul) {
        this.foul = foul;
        return this;
    }
}
