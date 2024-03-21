package com.threeNerds.basketballDiary.mvc.authUser.service.dto;


import lombok.Getter;

/**
 * 팀 가입 초대를 생성, 수정, 취소하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class TeamInvitationCommand {
    /** 팀가입요청Seq */
    private final Long teamJoinRequestSeq;
    /** 사용자Seq */
    private final Long userSeq;

    private TeamInvitationCommand(Long teamJoinRequestSeq, Long userSeq ) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        this.userSeq            = userSeq;
    }

    public static TeamInvitationCommand ofApproval(Long teamJoinRequestSeq, Long userSeq ) {
        return new TeamInvitationCommand( teamJoinRequestSeq, userSeq );
    }

    public static TeamInvitationCommand ofRejection(Long teamJoinRequestSeq, Long userSeq ) {
        return new TeamInvitationCommand( teamJoinRequestSeq, userSeq );
    }

}
