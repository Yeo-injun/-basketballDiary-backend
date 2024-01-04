package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import lombok.Getter;

@Getter
public class GetGameBasicInfoResponse {

    private final Long gameSeq;               // 게임Seq
    private final String gameYmd;             // 게임 생성일자
    private final String gameStartTime;       // 게임 시작시간
    private final String gameEndTime;         // 게임 종료시간
    private final String gamePlaceName;       // 게임 장소이름
    private final String gamePlaceAddress;    // 게임 장소주소

    public GetGameBasicInfoResponse ( Game game ) {
        this.gameSeq            = game.getGameSeq();             // 게임Seq
        this.gameYmd            = game.getGameYmd();             // 게임 생성일자
        this.gameStartTime      = game.getGameStartTime();       // 게임 시작시간
        this.gameEndTime        = game.getGameEndTime();         // 게임 종료시간
        this.gamePlaceName      = game.getGamePlaceName();       // 게임 장소이름
        this.gamePlaceAddress   = game.getGamePlaceAddress();    // 게임 장소주소
    }
}
