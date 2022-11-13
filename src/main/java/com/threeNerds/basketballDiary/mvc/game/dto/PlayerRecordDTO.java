package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerRecordDTO {
    private Long userSeq;
    private String name;
    private String backNumber;
    private String positionCode;
    private String userImage;
    private String quarterCode;
    private Long freeThrow;
    private Long twoPoint;
    private Long threePoint;
    private Long totalPoint;
    private Long assist;
    private Long rebound;
    private Long steal;
    private Long block;
    private Long turnover;
    private Long foul;

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
    public PlayerRecordDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public PlayerRecordDTO freeThrow(Long freeThrow) {
        this.freeThrow = freeThrow;
        return this;
    }
    public PlayerRecordDTO twoPoint(Long twoPoint) {
        this.twoPoint = twoPoint;
        return this;
    }
    public PlayerRecordDTO threePoint(Long threePoint) {
        this.threePoint = threePoint;
        return this;
    }
    public PlayerRecordDTO totalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
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
