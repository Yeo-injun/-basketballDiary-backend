package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GameRecordDTO {

    private Long gameSeq;
    private String gameYmd;
    private String gamePlaceAddress;
    private String gamePlaceName;
    private String gameTypeCode;
    private String gameTypeCodeName;

    private List<GameJoinTeamRecordDTO> gameJoinTeams;
}
