package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.game.dto.GameCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.javassist.compiler.CompileError;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private Long gameSeq;               /* 게임Seq */
    private Long creatorTeamMemberSeq;  /* 게임생성팀원Seq */
    private String gameRecordStateCode; /* 게임기록상태코드 */
    private String gameTypeCode;        /* 게임유형코드 */
    private String gameYmd;             /* 게임일자 */
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String gamePlaceAddress;    /* 게임장소주소 */
    private String gamePlaceName;       /* 게임장소명 */
    private String sidoCode;            /* 시도코드 */
    private String sigunguCode;         /* 시군구코드 */
    private LocalDate regDate;          /* 등록일시 - TimeStamp로 찍어주기 */

    /** 쿼터별 경기시간 */
    private String quarterTime1;
    private String quarterTime2;
    private String quarterTime3;
    private String quarterTime4;

    /** 게임생성
     * - 게임기록상태코드 기본값 : 게임생성(01)
     * - 게임유형코드 기본값 : 자체전(01)
     **/
    public static Game createDefault(GameCreationDTO gcDTO)
    {
        // TODO dto에 있는 값들 null체크하기
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return Game.builder()
                .creatorTeamMemberSeq(gcDTO.getCreatorTeamMemberSeq())
                .gameRecordStateCode(GameRecordStateCode.CREATION.getCode())
                .gameTypeCode(GameTypeCode.SELF_GAME.getCode())
                .gameYmd(gcDTO.getGameYmd())
                .gameStartTime(gcDTO.getGameStartTime())
                .gameEndTime(gcDTO.getGameEndTime())
                .gamePlaceAddress(gcDTO.getGamePlaceAddress())
                .gamePlaceName(gcDTO.getGamePlaceName())
                .sidoCode(gcDTO.getSidoCode())
                .sigunguCode(gcDTO.getSigunguCode())
                .regDate(now)
                .build();
    }

    /** 게임유형이 대회인지 체크 */
    public boolean isCompetitionType() {
        // 게임 객체가 게임유형코드 정보를 가지고 있는지 체크
        if (Optional.ofNullable(this.gameTypeCode).isEmpty()) {
            return false;
        }
        String competitionTypeCode = GameTypeCode.COMPETITION.getCode();
        return competitionTypeCode.equals(this.getGameTypeCode());
    }

    // TODO 에레메세지 재정의 필요
    public String getQuarterTime( QuarterCode quarterCode ) {
        switch ( quarterCode ) {
            case FIRST  : return this.quarterTime1;
            case SECOND : return this.quarterTime2;
            case THIRD  : return this.quarterTime3;
            case FOURTH : return this.quarterTime4;
        default     : throw new CustomException( Error.INVALID_PARAMETER );
        }
    }

    // TODO 에레메세지 재정의 필요
    public Game( Long gameSeq, QuarterCode quarterCode, String quarterTime ) {
        this.gameSeq = gameSeq;
        switch ( quarterCode ) {
            case FIRST  : this.quarterTime1 = quarterTime; break;
            case SECOND : this.quarterTime2 = quarterTime; break;
            case THIRD  : this.quarterTime3 = quarterTime; break;
            case FOURTH : this.quarterTime4 = quarterTime; break;
            default     : throw new CustomException( Error.INVALID_PARAMETER );
        }
    }
}
