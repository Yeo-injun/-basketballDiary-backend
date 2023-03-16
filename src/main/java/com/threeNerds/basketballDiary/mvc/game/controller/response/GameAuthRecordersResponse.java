package com.threeNerds.basketballDiary.mvc.game.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GameAuthRecordersResponse {

    private String teamName;

    private String name;

    private String email;

    private String backNumber;

    private String auth;

    public GameAuthRecordersResponse teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public GameAuthRecordersResponse name(String name){
        this.name = name;
        return this;
    }

    public GameAuthRecordersResponse email(String email){
        this.email = email;
        return this;
    }

    public GameAuthRecordersResponse backNumber(String backNumber){
        this.backNumber = backNumber;
        return this;
    }

    public GameAuthRecordersResponse auth(String auth){
        this.auth = auth;
        return this;
    }
}
