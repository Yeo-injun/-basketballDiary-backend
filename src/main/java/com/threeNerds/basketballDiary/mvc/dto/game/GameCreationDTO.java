package com.threeNerds.basketballDiary.mvc.dto.game;

import lombok.Getter;

@Getter
public class GameCreationDTO {
    /** TEAM 테이블 */
    private Long teamSeq;               /* 팀Seq - 소속팀이 맞는지 확인용 */

    /** GAME 테이블 */
    private Long gameSeq;               /* 게임Seq */
    private Long creatorTeamMemberSeq;  /* 게임생성팀원Seq */
    private String gameYmd;             /* 게임일자 */
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String gamePlaceAddress;    /* 게임장소주소 */
    private String gamePlaceName;       /* 게임장소명 */
    private String sidoCode;            /* 시도코드 */
    private String sigunguCode;         /* 시군구코드 */

    public GameCreationDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }
}
