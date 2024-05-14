package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameCreationCommand {

    private Long userSeq;               /* 사용자Seq - 세션값 할당 */
    private Long teamSeq;               /* 팀Seq - 소속팀이 맞는지 확인용 */
    private String gameYmd;             /* 게임일자 */
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String gamePlaceAddress;    /* 게임장소주소 */
    private String gamePlaceName;       /* 게임장소명 */
    private String sidoCode;            /* 시도코드 */
    private String sigunguCode;         /* 시군구코드 */

}