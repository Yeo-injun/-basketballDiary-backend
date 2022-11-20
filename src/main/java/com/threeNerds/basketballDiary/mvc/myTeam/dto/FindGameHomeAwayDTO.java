package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class FindGameHomeAwayDTO {

    private String teamName;

    private String homeTown;

    private String teamLeaderName;

    public FindGameHomeAwayDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public FindGameHomeAwayDTO homeTown(String homeTown){
        this.homeTown = homeTown;
        return this;
    }

    public FindGameHomeAwayDTO teamLeaderName(String teamLeaderName){
        this.teamLeaderName =  teamLeaderName;
        return this;
    }
}
