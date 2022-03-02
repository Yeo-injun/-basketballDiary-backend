package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

/**
 * Author: 강창기
 * 설명: 소속팀 Controller에서 팀원 및 운영진의 정보를 관리하는 DTO
 */

@Getter
public class MemberDTO {

    /* 유저 pk */
    private Long userSeq;
    /* 팀멤버 pk */
    private Long teamMemberSeq;
    /* 팀 pk */
    private Long teamSeq;
    /* 팀권한코드 */
    private Long teamAuthCode;
    /* 이름 */
    private String name;
    /* 생년월일 */
    private String birthYmd;
    /* 신장 */
    private String height;
    /* 몸무게 */
    private String weight;
    /* 등번호 */
    private String backNumber;
    /* 가입일자 */
    private String joinYmd;
    /* 경기참여횟수 */
    private Integer totGame;

    /* 페이징 처리를 위한 VO */
    private PagerDTO pagerDTO;

    public MemberDTO userSeq (Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public MemberDTO teamMemberSeq (Long teamMemberSeq) {
        this.teamMemberSeq = teamMemberSeq;
        return this;
    }

    public MemberDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public MemberDTO teamAuthCode (Long teamAuthCode) {
        this.teamAuthCode = teamAuthCode;
        return this;
    }

    public MemberDTO name (String name) {
        this.name = name;
        return this;
    }

    public MemberDTO birthYmd (String birthYmd) {
        this.birthYmd = birthYmd;
        return this;
    }

    public MemberDTO height (String height) {
        this.height = height;
        return this;
    }

    public MemberDTO weight (String weight) {
        this.weight = weight;
        return this;
    }

    public MemberDTO backNumber (String backNumber) {
        this.backNumber = backNumber;
        return this;
    }

    public MemberDTO joinYmd (String joinYmd) {
        this.joinYmd = joinYmd;
        return this;
    }

    public MemberDTO totGame (Integer totGame) {
        this.totGame = totGame;
        return this;
    }

    public MemberDTO pagerVO (PagerDTO pagerDTO) {
        this.pagerDTO = pagerDTO;
        return this;
    }
}
