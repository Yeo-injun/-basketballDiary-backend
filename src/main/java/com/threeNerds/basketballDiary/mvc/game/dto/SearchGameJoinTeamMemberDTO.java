package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

/**
 * 경기업무 조회용 DTO
 * 경기관련 SEQ 및 코드를 관리할 수 있다.
 * @author 강창기
 */
@Getter
public class SearchGameJoinTeamMemberDTO {
    /** 경기 SEQ */
    private Long gameSeq;
    /** 쿼터코드 */
    private String quarterCode;
    /** 홈어웨이코드 */
    private String homeAwayCode;

    public SearchGameJoinTeamMemberDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }
    public SearchGameJoinTeamMemberDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
    public SearchGameJoinTeamMemberDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
