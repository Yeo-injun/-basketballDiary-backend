package com.threeNerds.basketballDiary.mvc.dto;


import lombok.Getter;

@Getter
public class JoinRequestDTO {
    // 팀SEQ
    private Long teamSeq;
    // 사용자SEQ
    private Long userSeq;
    // 팀가입요청SEQ
    private Long teamJoinRequestSeq;

    public JoinRequestDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public JoinRequestDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public JoinRequestDTO teamJoinRequestSeq(Long teamJoinRequestSeq) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        return this;
    }
}
