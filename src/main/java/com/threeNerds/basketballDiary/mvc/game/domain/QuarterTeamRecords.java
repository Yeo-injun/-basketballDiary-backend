package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuarterTeamRecords {

    private final QuarterCode FIRST_QUARTER = QuarterCode.FIRST;
    private final QuarterCode SECOND_QUARTER = QuarterCode.SECOND;
    private final QuarterCode THIRD_QUARTER = QuarterCode.THIRD;
    private final QuarterCode FOURTH_QUARTER = QuarterCode.FOURTH;

    final private int SCORE_ONE = 1;
    final private int SCORE_TWO = 2;
    final private int SCORE_THREE = 3;

    private Long quarterTeamRecordsSeq; // 쿼터팀기록Seq
    private Long gameSeq;               // 게임Seq
    private String homeAwayCode;        // 홈에웨이코드
    private Long gameJoinTeamSeq;       // 게임참가팀Seq

    private String quarterCode;         // 쿼터코드
    private String quarterTime;         // 쿼터시간 TODO 제거할 속성.. 게임테이블에서 관리할 예정

    private int score;
    private int freeThrow;
    private int twoPoint;
    private int threePoint;
    private int assist;
    private int rebound;
    private int steal;
    private int block;
    private int turnover;
    private int foul;

    /** 홈팀 기록 생성 ( 선수기록을 팀기록으로 합산 처리 ) */
    public static QuarterTeamRecords ofHome( Long gameSeq, QuarterCode quarterCode, List<QuarterPlayerRecords> playerRecords ) {
        return QuarterTeamRecords.of( gameSeq, quarterCode, HomeAwayCode.HOME_TEAM, playerRecords );
    }

    /** 어웨이팀 기록 생성 ( 선수기록을 팀기록으로 합산 처리 ) */
    public static QuarterTeamRecords ofAway( Long gameSeq, QuarterCode quarterCode, List<QuarterPlayerRecords> playerRecords ) {
        return QuarterTeamRecords.of( gameSeq, quarterCode, HomeAwayCode.AWAY_TEAM, playerRecords );
    }

    public boolean is1stQuater() {
        return checkQuarter( FIRST_QUARTER );
    }
    public boolean is2ndQuater() {
        return checkQuarter( SECOND_QUARTER );
    }
    public boolean is3rdQuater() {
        return checkQuarter( THIRD_QUARTER );
    }
    public boolean is4thQuater() {
        return checkQuarter( FOURTH_QUARTER );
    }
    private boolean checkQuarter( QuarterCode codeType ) {
        return codeType.getCode().equals( this.quarterCode );
    }

    private static QuarterTeamRecords of( Long gameSeq, QuarterCode quarterCode, HomeAwayCode homeAwayCode, List<QuarterPlayerRecords> playerRecords ) {
        QuarterTeamRecords teamRecords = QuarterTeamRecords.builder()
                .gameSeq(       gameSeq )
                .quarterCode(   quarterCode.getCode() )
                .homeAwayCode(  homeAwayCode.getCode() )
                .build();
        teamRecords.sumRecordsByHomeAwayCode( playerRecords );
        return teamRecords;
    }

    /** 선수들의 쿼터기록을 팀기록으로 합산 */
    private void sumRecordsByHomeAwayCode( List<QuarterPlayerRecords> playerRecords ) {
        playerRecords.stream()
            .filter( player ->
                      this.gameSeq == player.getGameSeq().longValue()
                   && this.quarterCode.equals( player.getQuarterCode() )
                   && this.homeAwayCode.equals( player.getHomeAwayCode() )
            )
            .forEach( player -> {
                /** 선수 기록을 팀기록에 반영 */
                this.freeThrow      += player.getFreeThrow();
                this.twoPoint       += player.getTwoPoint();
                this.threePoint     += player.getThreePoint();
                this.assist         += player.getAssist();
                this.rebound        += player.getRebound();
                this.steal          += player.getSteal();
                this.block          += player.getBlock();
                this.turnover       += player.getTurnover();
                this.foul           += player.getFoul();

                /** 팀의 총득점 계산 */
                calculateQuarterTotalScore();
            });
    }
    /** 한 쿼터 동안 팀의 총 득점 계산 */
    public void calculateQuarterTotalScore() {
        int freeThrowScore  = this.freeThrow * SCORE_ONE;
        int twoPointScore   = this.twoPoint * SCORE_TWO;
        int threePointScore = this.threePoint * SCORE_THREE;

        // 현재 쿼터의 총 득점 계산
        this.score = freeThrowScore + twoPointScore + threePointScore;
    }

    /** 쿼터팀레코드 초기화 */
    public QuarterTeamRecords( Long gameSeq, String homeAwayCode, Long gameJoinTeamSeq, String quarterCode ) {
        this.gameSeq = gameSeq;
        this.homeAwayCode = homeAwayCode;
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        this.quarterCode = quarterCode;
        this.quarterTime = "0000";
    }
}
