package com.threeNerds.basketballDiary.mvc.user.service.dto;


import lombok.Getter;

/**
 * 팀 초대를 조회하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class TeamInvitationQuery {
    /** 사용자Seq */
    private final Long userSeq;

    private TeamInvitationQuery( Long userSeq ) {
        this.userSeq            = userSeq;
    }

    public static TeamInvitationQuery ofUser(Long userSeq) {
        return new TeamInvitationQuery( userSeq );
    }
}
