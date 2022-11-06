package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class CmnMyTeamDTO {
    /**************
     * USER 테이블 */
    private Long userSeq;

    public CmnMyTeamDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    /**************
     * TEAM 테이블 */
    private Long teamSeq;

    public CmnMyTeamDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }
    /*********************
     * TEAM_MEMBER 테이블 */
    private Long teamMemberSeq;

    public CmnMyTeamDTO teamMemberSeq(Long teamMemberSeq) {
        this.teamMemberSeq = teamMemberSeq;
        return this;
    }

    /***************************
     * TEAM_JOIN_REQUEST 테이블 */
    private Long teamJoinRequestSeq;
    private String joinRequestTypeCode;
    private String joinRequestStateCode;

    public CmnMyTeamDTO teamJoinRequestSeq(Long teamJoinRequestSeq) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        return this;
    }

    public CmnMyTeamDTO joinRequestTypeCode (String joinRequestTypeCode) {
        this.joinRequestTypeCode = joinRequestTypeCode;
        return this;
    }

    public CmnMyTeamDTO joinRequestStateCode (String joinRequestStateCode) {
        this.joinRequestStateCode = joinRequestStateCode;
        return this;
    }


}
