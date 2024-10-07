package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.type.GameTypeCode;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;

import lombok.Getter;


@Getter
public class GameDetailDTO {

    private Long gameSeq;               /* 게임Seq */
    private Long creatorTeamMemberSeq;  /* 게임생성팀원Seq - 경기를 생성한 팀의 팀원Seq */
    private String gameRecordStateCode; /* 게임기록상태코드 */
    private String gameRecordStateCodeName; /* 게임기록상태코드명 */
    private String gameTypeCode;        /* 경기유형코드 */
    private String gameTypeCodeName;    /* 경기유형코드명 */
    private String gameYmd;             /* 게임일자 */
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String gamePlaceAddress;    /* 게임장소주소 */
    private String gamePlaceName;       /* 게임장소명 */
    private String sidoCode;            /* 시도코드 */
    private String sigunguCode;         /* 시군구코드 */

    public static GameDetailDTO of( Game game ) {
        GameDetailDTO gameDetail = new GameDetailDTO();
        gameDetail.gameSeq              = game.getGameSeq();
        gameDetail.creatorTeamMemberSeq = game.getCreatorTeamMemberSeq();
        gameDetail.gameRecordStateCode      = game.getGameRecordStateCode();
        gameDetail.gameRecordStateCodeName  = GameRecordStateCode.nameOf( game.getGameRecordStateCode() );
        gameDetail.gameTypeCode         = game.getGameTypeCode();
        gameDetail.gameTypeCodeName     = GameTypeCode.nameOf( game.getGameTypeCode() );
        gameDetail.gameYmd              = game.getGameYmd();
        gameDetail.gameStartTime        = game.getGameStartTime();
        gameDetail.gameEndTime          = game.getGameEndTime();
        gameDetail.gamePlaceAddress     = game.getGamePlaceAddress();
        gameDetail.gamePlaceName        = game.getGamePlaceName();
        gameDetail.sidoCode             = game.getSidoCode();
        gameDetail.sigunguCode          = game.getSigunguCode();
        return gameDetail;
    }

}
