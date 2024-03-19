package com.threeNerds.basketballDiary.mvc.authUser.service.dto;


import lombok.Getter;

/**
 * 가입요청을 생성, 수정, 취소하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class JoinRequestQueryDTO {
    /** 사용자Seq */
    private final Long userSeq;

    private JoinRequestQueryDTO( Long userSeq ) {
        this.userSeq            = userSeq;
    }

    public static JoinRequestQueryDTO of( Long userSeq ) {
        return new JoinRequestQueryDTO( userSeq );
    }
}
