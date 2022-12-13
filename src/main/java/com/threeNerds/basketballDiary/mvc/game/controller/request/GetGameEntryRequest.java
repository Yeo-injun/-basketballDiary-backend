package com.threeNerds.basketballDiary.mvc.game.controller.request;

import lombok.Getter;

@Getter
public class GetGameEntryRequest {

    private Long gameJoinTeamSeq;
    private String quarterCode;
}
