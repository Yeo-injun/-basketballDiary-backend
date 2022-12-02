package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MatchPlayersInfoDTO {

    private Long gameSeq;

    private Long teamSeq;

    private String homeAwayCode;

    private List<PlayerInfoDTO> playersInfo;

    public MatchPlayersInfoDTO(Long gameSeq, Long teamSeq, String homeAwayCode, List<PlayerInfoDTO> playersInfo) {
        this.gameSeq = gameSeq;
        this.teamSeq = teamSeq;
        this.homeAwayCode = homeAwayCode;
        this.playersInfo = playersInfo.stream()
                .collect(Collectors.toList());
    }

    public MatchPlayersInfoDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public MatchPlayersInfoDTO teamSeq(Long teamSeq){
        this.teamSeq = teamSeq;
        return this;
    }

    public MatchPlayersInfoDTO homeAwayCode(String homeAwayCode){
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public MatchPlayersInfoDTO playersInfo(List<PlayerInfoDTO> playersInfo){
        this.playersInfo = playersInfo.stream()
                .collect(Collectors.toList());
        return this;
    }
}
