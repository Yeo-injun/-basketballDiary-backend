package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import lombok.Getter;

@Getter
public class TeamQuarterRecordsDTO {

    private Long gameJoinTeamSeq;
    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private String quarterTime;
    private int score;
    private int foul;

    public void setHomeAwayCode (String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
    }

    public TeamQuarterRecordsDTO gameJoinTeamSeq (Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public TeamQuarterRecordsDTO teamName (String teamName) {
        this.teamName = teamName;
        return this;
    }

    public TeamQuarterRecordsDTO homeAwayCode (String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
        return this;
    }

    public TeamQuarterRecordsDTO quarterTime (String quarterTime) {
        this.quarterTime = quarterTime;
        return this;
    }

    public TeamQuarterRecordsDTO score (int score) {
        this.score = score;
        return this;
    }

    public TeamQuarterRecordsDTO foul (int foul) {
        this.foul = foul;
        return this;
    }}
