package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class GameInfoDTO {
    private String gameYmd;             /*게임 생성날짜*/
    private String gameStartTime;       /*게임 시작시간*/
    private String gameEndTime;         /*게임 종료시간*/
    private String gamePlaceName;       /*게임 장소이름*/
    private String gamePlaceAddress;    /*게임 장소주소*/
}
