package com.threeNerds.basketballDiary.mvc.authUser.service.dto;


import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 가입요청을 생성, 수정, 취소하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class JoinRequestCommandDTO {
    /** 팀가입요청Seq */
    private final Long teamJoinRequestSeq;
    /** 팀Seq */
    private final Long teamSeq;
    /** 사용자Seq */
    private final Long userSeq;

    private JoinRequestCommandDTO( Long teamJoinRequestSeq, Long teamSeq, Long userSeq ) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        this.teamSeq            = teamSeq;
        this.userSeq            = userSeq;
    }

    public static JoinRequestCommandDTO ofCreation( Long teamSeq, Long userSeq ) {
        return new JoinRequestCommandDTO( null, teamSeq, userSeq );
    }

    public static JoinRequestCommandDTO ofCancel( Long teamJoinRequestSeq, Long userSeq ) {
        return new JoinRequestCommandDTO( teamJoinRequestSeq, null, userSeq );
    }

}
