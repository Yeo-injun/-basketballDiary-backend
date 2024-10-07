package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameDetailDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameQuery;
import lombok.Getter;

@Getter
public class GetGameBasicInfoResponse {

    private final Long gameSeq;               // 게임Seq
    private final String gameYmd;             // 게임 생성일자
    private final String gameStartTime;       // 게임 시작시간
    private final String gameEndTime;         // 게임 종료시간
    private final String gamePlaceName;       // 게임 장소이름
    private final String gamePlaceAddress;    // 게임 장소주소

    public GetGameBasicInfoResponse ( GameQuery.Result result ) {
        GameDetailDTO gameInfo = result.getGameInfo();
        this.gameSeq            = gameInfo.getGameSeq();             // 게임Seq
        this.gameYmd            = gameInfo.getGameYmd();             // 게임 생성일자
        this.gameStartTime      = gameInfo.getGameStartTime();       // 게임 시작시간
        this.gameEndTime        = gameInfo.getGameEndTime();         // 게임 종료시간
        this.gamePlaceName      = gameInfo.getGamePlaceName();       // 게임 장소이름
        this.gamePlaceAddress   = gameInfo.getGamePlaceAddress();    // 게임 장소주소
    }
}
