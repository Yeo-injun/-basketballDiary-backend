package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MatchPlayersInfoDTO {

    private Long gameSeq;

    private Long teamSeq;

    private String homeAwayCode;

    private List<PlayerInfoDTO> playersInfo;

}
