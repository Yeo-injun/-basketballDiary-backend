package com.threeNerds.basketballDiary.mvc.dto.myTeam;

import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import lombok.Getter;

@Getter
public class MyTeamTempDTO {

    /**************
     * TEAM 테이블 */
    private Long teamSeq;

    public MyTeamTempDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }
    /*********************
     * TEAM_MEMBER 테이블 */
    private Long teamMemberSeq;

    public MyTeamTempDTO teamMemberSeq(Long teamMemberSeq) {
        this.teamMemberSeq = teamMemberSeq;
        return this;
    }

    /***************************
     * TEAM_JOIN_REQUEST 테이블 */
    private Long teamJoinRequestSeq;
    private String joinRequestTypeCode;
    private String joinRequestStateCode;

    public MyTeamTempDTO teamJoinRequestSeq(Long teamJoinRequestSeq) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        return this;
    }

    public MyTeamTempDTO joinRequestTypeCode (String joinRequestTypeCode) {
        this.joinRequestTypeCode = joinRequestTypeCode;
        return this;
    }

    public MyTeamTempDTO joinRequestStateCode (String joinRequestStateCode) {
        this.joinRequestStateCode = joinRequestStateCode;
        return this;
    }

}
