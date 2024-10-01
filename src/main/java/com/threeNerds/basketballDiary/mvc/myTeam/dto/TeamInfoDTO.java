package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import lombok.Getter;

@Getter
public class TeamInfoDTO {

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

    public TeamInfoDTO( Team team ) {
        this.teamSeq        = team.getTeamSeq();
        this.teamName       = team.getTeamName();
        this.teamImagePath  = team.getTeamImagePath();
        this.hometown       = team.getHometown();
        this.sidoCode       = team.getSidoCode();
        this.sigunguCode    = team.getSigunguCode();
        this.foundationYmd  = team.getFoundationYmd();
        this.introduction   = team.getIntroduction();
    }
}
