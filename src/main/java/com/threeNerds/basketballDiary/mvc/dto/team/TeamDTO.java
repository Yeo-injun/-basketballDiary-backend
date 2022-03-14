package com.threeNerds.basketballDiary.mvc.dto.team;

import lombok.Getter;

@Getter
public class TeamDTO {

    private String leaderId;

    private String teamName;

    private String hometown;

    private String introduction;

    private String sidoCode;

    private String sigunguCode;

    public TeamDTO leaderId(String leaderId){
        this.leaderId = leaderId;
        return this;
    }
    public TeamDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }
    public TeamDTO hometown(String hometown){
        this.hometown = hometown;
        return this;
    }
    public TeamDTO introduction(String introduction){
        this.introduction = introduction;
        return this;
    }
    public TeamDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public TeamDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
}

