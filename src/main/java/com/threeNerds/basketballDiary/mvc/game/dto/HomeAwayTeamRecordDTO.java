package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

/**
 * Home, Away 팀의 기록 DTO
 * @author 강창기
 */
@Getter
public class HomeAwayTeamRecordDTO {
    // 게임Seq
    private String gameSeq;
    // 게임일자
    private String gameYmd;
    // 게임시작시간
    private String gameStartTime;
    // 게임종료시간
    private String gameEndTime;

    /**
     * 쿼터별 팀 기록을 조회할 경우 사용.
     */
    private Long quarterTeamRecordsSeq;
    // 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
    private String quarterCode;
    // 쿼터시간(경기시간)
    private String quarterTime;

    // 홈_팀Seq
    private Long homeTeamSeq;
    // 홈_팀명
    private String homeTeamName;
    // 홈_총점수
    private Integer homeTotalScore;
    // 홈_팀파울
    private Integer homeFoul;

    // 어웨이_팀Seq
    private Long awayTeamSeq;
    // 어웨이_팀명
    private String awayTeamName;
    // 어웨이_총점수
    private Integer awayTotalScore;
    // 어웨이_팀파울
    private Integer awayFoul;

    public HomeAwayTeamRecordDTO gameSeq(String gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public HomeAwayTeamRecordDTO gameYmd(String gameYmd) {
        this.gameYmd = gameYmd;
        return this;
    }

    public HomeAwayTeamRecordDTO gameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
        return this;
    }

    public HomeAwayTeamRecordDTO gameEndTime(String gameEndTime) {
        this.gameEndTime = gameEndTime;
        return this;
    }

    public HomeAwayTeamRecordDTO quarterTeamRecordsSeq(Long quarterTeamRecordsSeq) {
        this.quarterTeamRecordsSeq = quarterTeamRecordsSeq;
        return this;
    }

    public HomeAwayTeamRecordDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public HomeAwayTeamRecordDTO quarterTime(String quarterTime) {
        this.quarterTime = quarterTime;
        return this;
    }

    public HomeAwayTeamRecordDTO homeTeamSeq(Long homeTeamSeq) {
        this.homeTeamSeq = homeTeamSeq;
        return this;
    }

    public HomeAwayTeamRecordDTO homeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
        return this;
    }

    public HomeAwayTeamRecordDTO homeTotalScore(Integer homeTotalScore) {
        this.homeTotalScore = homeTotalScore;
        return this;
    }

    public HomeAwayTeamRecordDTO homeFoul(Integer homeFoul) {
        this.homeFoul = homeFoul;
        return this;
    }

    public HomeAwayTeamRecordDTO awayTeamSeq(Long awayTeamSeq) {
        this.awayTeamSeq = awayTeamSeq;
        return this;
    }

    public HomeAwayTeamRecordDTO awayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
        return this;
    }

    public HomeAwayTeamRecordDTO awayTotalScore(Integer awayTotalScore) {
        this.awayTotalScore = awayTotalScore;
        return this;
    }

    public HomeAwayTeamRecordDTO awayFoul(Integer awayFoul) {
        this.awayFoul = awayFoul;
        return this;
    }

}
