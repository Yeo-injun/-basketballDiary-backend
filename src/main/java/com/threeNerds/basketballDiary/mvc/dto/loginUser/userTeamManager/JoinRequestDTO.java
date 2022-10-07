package com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager;


import com.threeNerds.basketballDiary.constant.code.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.JoinRequestTypeCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinRequestDTO {
    /***********
     * 기타 정보 *
     ***********/
    // 팀장이름
    private String leaderName;
    // 가입요청 유형코드명
    private String joinRequestTypeCodeName;
    // 가입요청 상태코드명
    private String joinRequestStateCodeName;

    /**************************
     *  TeamJoinRequest 테이블 *
     **************************/
    // 팀가입요청SEQ
    private Long teamJoinRequestSeq;

    // 팀SEQ
    private Long teamSeq;

    // 사용자SEQ
    private Long userSeq;

    // 가입요청 유형코드
    private String joinRequestTypeCode;

    // 가입요청상태 코드
    private String joinRequestStateCode;

    // 가입요청일시
    private LocalDate requestDate;

    /**************
     * Team 테이블 *
     **************/
    // 팀명
    private String teamName;

    // 연고지
    private String hometown;

    // 창단일자
    private String foundationYmd;

    public JoinRequestDTO leaderName(String leaderName) {
        this.leaderName = leaderName;
        return this;
    }

    public JoinRequestDTO setCodeNameByInstanceCodeValue()
    {
        String stateCode = this.joinRequestStateCode;
        String typeCode = this.joinRequestTypeCode;

        if (stateCode != null) {
            this.joinRequestStateCodeName = JoinRequestStateCode.nameOf(stateCode);
        }

        if (typeCode != null) {
            this.joinRequestTypeCodeName = JoinRequestTypeCode.nameOf(typeCode);
        }
        return this;
    }

    public JoinRequestDTO joinRequestTypeCodeName(String joinRequestTypeCode) {
        this.joinRequestTypeCodeName = JoinRequestTypeCode.nameOf(joinRequestTypeCode);
        return this;
    }

    public JoinRequestDTO joinRequestStateCodeName(String joinRequestStateCode) {
        this.joinRequestStateCodeName = JoinRequestStateCode.nameOf(joinRequestStateCode);
        return this;
    }

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


    public JoinRequestDTO joinRequestTypeCode(String joinRequestTypeCode) {
        this.joinRequestTypeCode = joinRequestTypeCode;
        return this;
    }

    public JoinRequestDTO joinRequestStateCode(String joinRequestStateCode) {
        this.joinRequestStateCode = joinRequestStateCode;
        return this;
    }

    public JoinRequestDTO requestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public JoinRequestDTO teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public JoinRequestDTO hometown(String hometown) {
        this.hometown = hometown;
        return this;
    }

    public JoinRequestDTO foundationYmd(String foundationYmd) {
        this.foundationYmd = foundationYmd;
        return this;
    }
}
