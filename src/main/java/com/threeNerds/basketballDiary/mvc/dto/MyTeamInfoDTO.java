package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

@Getter
public class MyTeamInfoDTO {
    // TEAM
    /* 팀 pk */
    private Long teamSeq;
    /* 팀명 */
    private String teamName;
    /* 팀 이미지 경로 */
    private String teamImagePath;
    /* 연고지 */
    private String hometown;
    /* 시도코드 */
    private String sidoCode;
    /* 시군구코드 */
    private String sigunguCode;
    /* 창단일 */
    private String foundationYmd;
    /* 팀 소개 */
    private String introduction;

    // TEAM_MEMBER
    /* 회원수 */
    private Integer totMember;

    public MyTeamInfoDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public MyTeamInfoDTO teamName (String teamName) {
        this.teamName = teamName;
        return this;
    }

    public MyTeamInfoDTO teamImagePath (String teamImagePath) {
        this.teamImagePath = teamImagePath;
        return this;
    }

    public MyTeamInfoDTO hometown (String hometown) {
        this.hometown = hometown;
        return this;
    }

    public MyTeamInfoDTO sidoCode (String sidoCode) {
        this.sidoCode = sidoCode;
        return this;
    }

    public MyTeamInfoDTO sigunguCode (String sigunguCode) {
        this.sigunguCode = sigunguCode;
        return this;
    }

    public MyTeamInfoDTO foundationYmd (String foundationYmd) {
        this.foundationYmd = foundationYmd;
        return this;
    }

    public MyTeamInfoDTO introduction (String introduction) {
        this.introduction = introduction;
        return this;
    }

    public MyTeamInfoDTO totMember (Integer totMember) {
        this.totMember = totMember;
        return this;
    }
}
