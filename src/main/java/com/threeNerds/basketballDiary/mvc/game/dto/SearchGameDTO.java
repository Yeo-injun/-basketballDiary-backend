package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

/**
 * 경기업무 조회용 DTO
 * 경기관련 SEQ 및 코드를 관리할 수 있다.
 * @author 강창기
 */
@Getter
public class SearchGameDTO {
    /** 경기 SEQ */
    private Long gameSeq;

    /** 팀 SEQ */
    private Long teamSeq;

    /** 쿼터별 선수기록 SEQ */
    private Long quarterPlayerRecordsSeq;
    /** 선수별 경기 SEQ */
    private Long gameJoinPlayerSeq;
    /** 팀별 경기 SEQ */
    private Long gameJoinTeamSeq;

    /** 쿼터코드 */
    private String quarterCode;
    /** 홈어웨이코드 */
    private String homeAwayCode;

    public SearchGameDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }
    public SearchGameDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }
    public SearchGameDTO quarterPlayerRecordsSeq(Long quarterPlayerRecordsSeq) {
        this.quarterPlayerRecordsSeq = quarterPlayerRecordsSeq;
        return this;
    }
    public SearchGameDTO gameJoinPlayerSeq(Long gameJoinPlayerSeq) {
        this.gameJoinPlayerSeq = gameJoinPlayerSeq;
        return this;
    }
    public SearchGameDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }
    public SearchGameDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
    public SearchGameDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
