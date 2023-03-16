package com.threeNerds.basketballDiary.mvc.game.controller.dto;

import com.threeNerds.basketballDiary.mvc.game.controller.request.GameAuthRecorderRequest;
import lombok.Getter;

@Getter
public class GameAuthRecorderDTO {

    private Long gameSeq;

    private String teamName;

    private String name;

    private String email;

    private String backNumber;

    private String auth;

    public GameAuthRecorderDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public GameAuthRecorderDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public GameAuthRecorderDTO name(String name){
        this.name = name;
        return this;
    }

    public GameAuthRecorderDTO email(String email){
        this.email = email;
        return this;
    }

    public GameAuthRecorderDTO backNumber(String backNumber){
        this.backNumber = backNumber;
        return this;
    }

    public GameAuthRecorderDTO auth(String auth){
        this.auth = auth;
        return this;
    }

    public static GameAuthRecorderDTO createGameAuthRecorderDTO(
            Long gameSeq,
            GameAuthRecorderRequest gameAuthRecorderRequest
    ){
        return new GameAuthRecorderDTO()
                .gameSeq(gameSeq)
                .teamName(gameAuthRecorderRequest.getTeamName())
                .name(gameAuthRecorderRequest.getName())
                .email(gameAuthRecorderRequest.getEmail())
                .backNumber(gameAuthRecorderRequest.getBackNumber())
                .auth(gameAuthRecorderRequest.getAuth());
    }
}
