package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

@Getter
public class ManagerDTO {

    /* 유저 pk */
    private Long userSeq;
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


    public ManagerDTO userSeq (Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public ManagerDTO teamAuthCode (Long teamAuthCode) {
        this.teamAuthCode = teamAuthCode;
        return this;
    }

    public ManagerDTO name (String name) {
        this.name = name;
        return this;
    }

    public ManagerDTO birthYmd (String birthYmd) {
        this.birthYmd = birthYmd;
        return this;
    }

    public ManagerDTO height (String height) {
        this.height = height;
        return this;
    }

    public ManagerDTO weight (String weight) {
        this.weight = weight;
        return this;
    }

    public ManagerDTO backNumber (String backNumber) {
        this.backNumber = backNumber;
        return this;
    }

    public ManagerDTO joinYmd (String joinYmd) {
        this.joinYmd = joinYmd;
        return this;
    }
}
