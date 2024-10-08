package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.MyTeamInfoQuery;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTeamInfoResponse {

    /* 팀정보 */
    private Long teamSeq;
    private String teamName;
    private String foundationYmd;
    private String hometown;
    private String sidoCode;
    private String sigunguCode;
    private String introduction;

    /* 팀로고 이미지 경로 */
    private String teamLogoImagePath;
    /* 팀원 수 */
    private Integer memberCount;
    /* 정기운동시간 */
    private List<TeamRegularExerciseDTO> regularExercises;

    public GetTeamInfoResponse( MyTeamInfoQuery.Result query ) {
        TeamInfoDTO teamInfo = query.getTeamInfo();
        this.teamSeq            = teamInfo.getTeamSeq();
        this.teamName           = teamInfo.getTeamName();
        this.foundationYmd      = teamInfo.getFoundationYmd();
        this.hometown           = teamInfo.getHometown();
        this.sidoCode           = teamInfo.getSidoCode();
        this.sigunguCode        = teamInfo.getSigunguCode();
        this.introduction       = teamInfo.getIntroduction();
        this.teamLogoImagePath  = teamInfo.getTeamImagePath();
        this.memberCount        = query.getMemberCount();
        this.regularExercises   = query.getRegularExercises();
    }
}
