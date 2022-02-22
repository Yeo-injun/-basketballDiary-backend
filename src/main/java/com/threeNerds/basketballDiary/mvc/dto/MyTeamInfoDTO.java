package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTeamInfoDTO {
    // TEAM
    /* 팀 pk */
    public Long teamSeq;
    /* 팀명 */
    public String teamName;
    /* 팀 이미지 경로 */
    public String teamImagePath;
    /* 연고지 */
    public String hometown;
    /* 시도코드 */
    public String sidoCode;
    /* 시군구코드 */
    public String sigunguCode;
    /* 창단일 */
    public String foundationYmd;
    /* 팀 소개 */
    public String introduction;

    // TEAM_MEMBER
    /* 회원수 */
    public Integer totMember;
}
