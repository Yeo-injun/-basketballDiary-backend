package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SearchOppenentsDTO {

    String sidoCode;    /*시도코드*/

    String teamName;    /*팀이름*/

    String leaderName;  /*리더이름*/

    public SearchOppenentsDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public SearchOppenentsDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }
    public SearchOppenentsDTO leaderName(String leaderName){
        this.leaderName = leaderName;
        return this;
    }
}
