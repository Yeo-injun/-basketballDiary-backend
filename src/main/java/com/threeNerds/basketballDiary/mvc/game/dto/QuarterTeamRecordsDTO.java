package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import lombok.Getter;

@Getter
public class QuarterTeamRecordsDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private String quarterCode;
    private String quarterCodeName;
    private String quarterTime;
    private Integer score;
    private Integer foul;

    public void setHomeAwayCode (String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
    }

    public void setQuarterCode (String quarterCodeName) {
        this.quarterCode = quarterCodeName;
        this.quarterCodeName = QuarterCode.nameOf(quarterCodeName);
    }

    public QuarterTeamRecordsDTO gameSeq (Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public QuarterTeamRecordsDTO gameJoinTeamSeq (Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public QuarterTeamRecordsDTO teamName (String teamName) {
        this.teamName = teamName;
        return this;
    }

    public QuarterTeamRecordsDTO homeAwayCode (String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
        return this;
    }

    public QuarterTeamRecordsDTO quarterCode (String quarterCode) {
        this.quarterCode = quarterCode;
        this.quarterCodeName = QuarterCode.nameOf(quarterCode);
        return this;
    }

    public QuarterTeamRecordsDTO quarterTime (String quarterTime) {
        this.quarterTime = quarterTime;
        return this;
    }

    public QuarterTeamRecordsDTO score (Integer score) {
        this.score = score;
        return this;
    }

    public QuarterTeamRecordsDTO foul (Integer foul) {
        this.foul = foul;
        return this;
    }}
