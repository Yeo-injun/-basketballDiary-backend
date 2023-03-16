package com.threeNerds.basketballDiary.mvc.game.controller.request;

import lombok.Getter;

@Getter
public class GameAuthRecorderRequest {

    private String teamName;

    private String name;

    private String email;

    private String backNumber;

    private String auth;
}
