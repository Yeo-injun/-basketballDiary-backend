package com.threeNerds.basketballDiary.mvc.user.service.dto;


import lombok.Getter;

/**
 * 가입요청을 생성, 수정, 취소하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class JoinRequestCommand {
    /** 팀가입요청Seq */
    private final Long teamJoinRequestSeq;
    /** 팀Seq */
    private final Long teamSeq;
    /** 사용자Seq */
    private final Long userSeq;

    private JoinRequestCommand(Long teamJoinRequestSeq, Long teamSeq, Long userSeq ) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        this.teamSeq            = teamSeq;
        this.userSeq            = userSeq;
    }

    public static JoinRequestCommand ofCreation(Long teamSeq, Long userSeq ) {
        return new JoinRequestCommand( null, teamSeq, userSeq );
    }

    public static JoinRequestCommand ofCancel(Long teamJoinRequestSeq, Long userSeq ) {
        return new JoinRequestCommand( teamJoinRequestSeq, null, userSeq );
    }

}
