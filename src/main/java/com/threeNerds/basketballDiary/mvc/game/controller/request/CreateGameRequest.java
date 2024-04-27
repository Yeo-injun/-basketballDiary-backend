package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.service.dto.GameCreationCommand;
import lombok.Getter;

@Getter
public class CreateGameRequest {

    private Long teamSeq;               /* 팀Seq - 소속팀이 맞는지 확인용 */
    private String gameYmd;             /* 게임일자 */
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String gamePlaceAddress;    /* 게임장소주소 */
    private String gamePlaceName;       /* 게임장소명 */
    private String sidoCode;            /* 시도코드 */
    private String sigunguCode;         /* 시군구코드 */

    public GameCreationCommand ofCommand( Long userSeq ) {
        return GameCreationCommand.builder()
                .userSeq(       userSeq )
                .teamSeq(       this.teamSeq )
                .gameYmd(       this.gameYmd )
                .gameStartTime( this.gameStartTime )
                .gameEndTime(   this.gameEndTime )
                .gamePlaceAddress(  this.gamePlaceAddress )
                .gamePlaceName(     this.gamePlaceName )
                .sidoCode(          this.sidoCode )
                .sigunguCode(       this.sigunguCode )
                .build();
    }
}
