package com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;

import java.util.List;

@Getter
public class MyTeamDTO {

    /* 소속팀 정보 */
    private MyTeamInfoDTO  myTeamInfo;

    /* 정기운동 목록 */
    private List<TeamRegularExercise> teamRegularExercisesList;

    public MyTeamDTO myTeamInfo (MyTeamInfoDTO myTeamInfo) {
        this.myTeamInfo = myTeamInfo;
        return this;
    }

    public MyTeamDTO teamRegularExercisesList (List<TeamRegularExercise> teamRegularExercisesList) {
        this.teamRegularExercisesList = teamRegularExercisesList;
        return this;
    }
}
