package com.threeNerds.basketballDiary.mvc.user.service.dto;


import lombok.Getter;

/**
 * 가입요청을 생성, 수정, 취소하기 위해 필요한 필드를 관리하는 DTO
 */
@Getter
public class JoinRequestQuery {
    /** 사용자Seq */
    private final Long userSeq;

    private JoinRequestQuery(Long userSeq ) {
        this.userSeq            = userSeq;
    }

    public static JoinRequestQuery of(Long userSeq ) {
        return new JoinRequestQuery( userSeq );
    }
}
