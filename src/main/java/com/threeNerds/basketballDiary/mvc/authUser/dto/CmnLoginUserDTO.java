package com.threeNerds.basketballDiary.mvc.authUser.dto;

import lombok.Getter;

@Getter
public class CmnLoginUserDTO {
    /** 사용자Seq */
    private Long userSeq;

    /**
     * TEAM_JOIN_REQUEST 테이블 */
    private Long teamJoinRequestSeq;

    /**
     * TEAM 테이블 */
    private Long teamSeq;


    /** 회원아이디 **/
    private String userId;
    /** 비밀번호 **/
    private String password;

    public CmnLoginUserDTO userSeq(Long userSeq){
        this.userSeq = userSeq;
        return this;
    }

    public CmnLoginUserDTO teamJoinRequestSeq(Long teamJoinRequestSeq){
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        return this;
    }

    public CmnLoginUserDTO teamSeq(Long teamSeq){
        this.teamSeq = teamSeq;
        return this;
    }

    public CmnLoginUserDTO userId(String userId){
        this.userId = userId;
        return this;
    }
    public CmnLoginUserDTO password(String password){
        this.password=password;
        return this;
    }
}
