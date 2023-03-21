package com.threeNerds.basketballDiary.mvc.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuarterTeamRecords {

    final private int SCORE_ONE = 1;
    final private int SCORE_TWO = 2;
    final private int SCORE_THREE = 3;

    private Long quarterTeamRecordsSeq; // 쿼터팀기록Seq
    private Long gameSeq;               // 게임Seq
    private String homeAwayCode;        // 홈에웨이코드
    private Long gameJoinTeamSeq;       // 게임참가팀Seq

    private String quarterCode;         // 쿼터코드
    private String quarterTime;         // 쿼터시간

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

    /** 쿼터팀레코드 초기화 */
    public QuarterTeamRecords( Long gameSeq, String homeAwayCode, Long gameJoinTeamSeq, String quarterCode ) {
        this.gameSeq = gameSeq;
        this.homeAwayCode = homeAwayCode;
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        this.quarterCode = quarterCode;
        this.quarterTime = "0000";
    }

    /** 한 쿼터 동안 팀의 모든 Stat 초기화 */
    public void initRecords()
    {
        this.score = 0;
        this.freeThrow = 0;
        this.twoPoint = 0;
        this.threePoint = 0;
        this.assist = 0;
        this.rebound = 0;
        this.steal = 0;
        this.block = 0;
        this.turnover = 0;
        this.foul = 0;
    }

    /** 한 쿼터 동안 팀의 총 득점 계산 */
    public void calculateQuarterTotalScore()
    {
        int freeThrowScore  = this.freeThrow * SCORE_ONE;
        int twoPointScore   = this.twoPoint * SCORE_TWO;
        int threePointScore = this.threePoint * SCORE_THREE;
        
        // 현재 쿼터의 총 득점 계산
        this.score = freeThrowScore + twoPointScore + threePointScore;
    }

    /** 플레이어 기록을 팀기록에 합산 */
    public void addPlayerRecordsStat(QuarterPlayerRecords quarterPlayerRecords) {
        /** 선수가 해당팀 소속인지 확인 */
        boolean isTeamMemebr = ( this.gameSeq.longValue() == quarterPlayerRecords.getGameSeq()
                                && this.homeAwayCode.equals( quarterPlayerRecords.getHomeAwayCode() ) );

        if ( !isTeamMemebr ) {
            // TODO 팀멤버가 아닌 경우 로그를 찍고 메소드 종료
            return;
        }

        /** 선수 기록을 팀기록에 반영 */
        this.freeThrow += quarterPlayerRecords.getFreeThrow();
        this.twoPoint += quarterPlayerRecords.getTwoPoint();
        this.threePoint += quarterPlayerRecords.getThreePoint();
        this.assist += quarterPlayerRecords.getAssist();
        this.rebound += quarterPlayerRecords.getRebound();
        this.steal += quarterPlayerRecords.getSteal();
        this.block += quarterPlayerRecords.getBlock();
        this.turnover += quarterPlayerRecords.getTurnover();
        this.foul += quarterPlayerRecords.getFoul();

        /** 팀의 총득점 계산 */
        calculateQuarterTotalScore();
    }
}
