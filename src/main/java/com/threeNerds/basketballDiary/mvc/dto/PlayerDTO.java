package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class PlayerDTO {
    // 팀가입요청SEQ
    private Long teamJoinRequestSeq;

    // 가입요청일시
    private Date requestDate;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 포지션코드
    private String positionCode;

    // 포지션코드명
    private String positionCodeName;

    // 키
    private Float height;

    // 몸무게
    private Float weight;

    // 가입요청상태코드
    private String joinRequestStateCode;

    // 가입요청상태코드명
    private String joinRequestStateCodeName;

    public PlayerDTO teamJoinRequestSeq (Long teamJoinRequestSeq) {
        this.teamJoinRequestSeq = teamJoinRequestSeq;
        return this;
    }

    public PlayerDTO requestDate (Date requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public PlayerDTO name (String name) {
        this.name = name;
        return this;
    }

    public PlayerDTO email (String email) {
        this.email = email;
        return this;
    }

    public PlayerDTO positionCode (String positionCode) {
        this.positionCode = positionCode;
        return this;
    }

    public PlayerDTO positionCodeName (String positionCodeName) {
        this.positionCodeName = positionCodeName;
        return this;
    }

    public PlayerDTO height (Float height) {
        this.height = height;
        return this;
    }

    public PlayerDTO weight (Float weight) {
        this.weight = weight;
        return this;
    }

    public PlayerDTO joinRequestStateCode (String joinRequestStateCode) {
        this.joinRequestStateCode = joinRequestStateCode;
        return this;
    }

    public PlayerDTO joinRequestStateCodeName (String joinRequestStateCodeName) {
        this.joinRequestStateCodeName = joinRequestStateCodeName;
        return this;
    }
}
